package com.github.springprac.service;

import com.github.springprac.respository.items.ElectronicStoreItemRepository;
import com.github.springprac.respository.items.ElectroniceStoreItemJpaRepository;
import com.github.springprac.respository.items.ItemEntity;
import com.github.springprac.respository.storeSales.StoreSales;
import com.github.springprac.respository.storeSales.StoreSalesJpaRepository;
import com.github.springprac.respository.storeSales.StoreSalesRepository;
import com.github.springprac.service.exceptions.NotAcceptException;
import com.github.springprac.service.exceptions.NotFoundException;
import com.github.springprac.service.mapper.ItemMapper;
import com.github.springprac.web.dto.BuyOrder;
import com.github.springprac.web.dto.Item;
import com.github.springprac.web.dto.ItemBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectronicStoreItemService {

    private final ElectroniceStoreItemJpaRepository electroniceStoreItemJpaRepository;
    private final ElectronicStoreItemRepository electronicStoreItemRepository;
    private final StoreSalesRepository storeSalesRepository;
    private final StoreSalesJpaRepository storeSalesJpaRepository;


    public List<Item> findAllItem() {
        List<ItemEntity> itemEntities = electroniceStoreItemJpaRepository.findAll();
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    public Integer savaItem(ItemBody itemBody) {
        ItemEntity itemEntity = ItemMapper.INSTANCE.idAndItemBodyToItemEntity(null,itemBody);
        ItemEntity itemEntityCreated;
        try{
            itemEntityCreated = electroniceStoreItemJpaRepository.save(itemEntity);
        }
        catch(RuntimeException e){throw new NotAcceptException("Item을 저장하는 도중에 Error 가 발생하였습니다.");
        }
        return itemEntityCreated.getId();
    }

    public Item findItemById(String id) {
        Integer idInt = Integer.parseInt(id);
        ItemEntity itemEntity = electroniceStoreItemJpaRepository.findById(idInt).orElseThrow(() -> new NotFoundException("해당 ID: " + idInt + "의 Item을 찾을 수 없습니다."));
        Item item = ItemMapper.INSTANCE.itemEntityToItem(itemEntity);
        return item;
    }

    public List<Item> findItemsByIds(List<String> ids) {
        List<ItemEntity> itemEntities = electroniceStoreItemJpaRepository.findAll();
        if (itemEntities.isEmpty()) throw new NotFoundException("아무 Items 들을 찾을 수 없습니다.");

        return itemEntities.stream()
                .map(ItemMapper.INSTANCE::itemEntityToItem)
                .filter((item -> ids.contains(item.getId())))
                .collect(Collectors.toList());
    }

    public void deleteItem(String id) {
        Integer idInt = Integer.parseInt(id);
        electroniceStoreItemJpaRepository.deleteById(idInt);
    }

    @Transactional(transactionManager = "tmJpa1")
    public Item updateItem(String id, ItemBody itemBody) {
        Integer idInt = Integer.valueOf(id);
        ItemEntity itemEntity = ItemMapper.INSTANCE.idAndItemBodyToItemEntity(null,itemBody);
        ItemEntity itemEntityUpdated = electroniceStoreItemJpaRepository.findById(idInt).orElseThrow(() -> new NotFoundException("해당 ID: " + idInt + "의 Item을 찾을 수 없습니다."));
        itemEntityUpdated.setItemBody(itemBody);

        return ItemMapper.INSTANCE.itemEntityToItem(itemEntityUpdated);

    }

    @Transactional(transactionManager = "tmJpa1")
    public Integer buyItems(BuyOrder buyOrder) {
        // 1. BuyOrder 에서 상품 ID와 수량을 얻어낸다.
        // 2. 상품을 조회하여 수량이 얼마나 있는 지 확인한다.
        // 3. 상품의 수량과 가격을 가지고 계산하여 총 가격을 구한다.
        // 4. 상품의 재고에 기존 계산한 재고를 구매하는 수량을 뺸다.
        // 5. 상품 구매하는 수량 * 가격 만큼 가계 매상으로 올린다.
        // (단, 재고가 아예 없거나 매장을 찾을 수 없으면 살 수 없다. )

        Integer itemId = buyOrder.getItemId();
        Integer itemNums = buyOrder.getItemNums();

        System.out.println("itemId: " + itemId);
        ItemEntity itemEntity = electroniceStoreItemJpaRepository.findById(itemId)
                .orElseThrow(()->new NotFoundException("해당 이름의 물건을 찾을 수 없습니다."));

        if (itemEntity.getStoreId() == null ) throw new RuntimeException("매장을 찾을 수 없습니다.");
        if (itemEntity.getStock() <= 0) throw new RuntimeException("상품의 재고가 없습니다.");

        Integer successBuyItemNums;
        if ( itemNums >= itemEntity.getStock() ) successBuyItemNums = itemEntity.getStock();
        else successBuyItemNums = itemNums;

        Integer totalPrice = successBuyItemNums * itemEntity.getPrice();

        // Item 재고 감소
        itemEntity.setStock(itemEntity.getStock() - successBuyItemNums);

        if (successBuyItemNums == 4)
        {
            log.error("4개를 구매하는건 허락하지않습니다.");
            throw new RuntimeException("4개를 구매하는건 허락하지않습니다.");
        }

        // 매장 매상 추가
        StoreSales storeSales = storeSalesJpaRepository.findById(itemEntity.getStoreId())
                        .orElseThrow(() -> new NotFoundException("요청하신 StoreId : " + itemEntity.getStoreId() + "에 해당하는 StoreSale 없습니다."));
        storeSales.setAmount(storeSales.getAmount() + totalPrice);
        return successBuyItemNums;
    }

    public List<Item> findItemsByTypes(List<String> types) {
        List<ItemEntity> itemEntities = electroniceStoreItemJpaRepository.findItemEntitiesByTypeIn(types);
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    public List<Item> findItemOrderByPrices(Integer maxValue) {
        List<ItemEntity> itemEntities = electroniceStoreItemJpaRepository.findItemEntitiesByPriceLessThanEqualOrderByPriceAsc(maxValue);
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    public Page<Item> findAllWithPageable(Pageable pageable) {
        Page<ItemEntity> itemEntities = electroniceStoreItemJpaRepository.findAll(pageable);
        return itemEntities.map(ItemMapper.INSTANCE::itemEntityToItem);
    }

    public Page<Item> findAllWithPageable(List<String> types, Pageable pageable) {
        Page<ItemEntity> itemEntities = electroniceStoreItemJpaRepository.findAllByTypeIn(types, pageable);
        return itemEntities.map(ItemMapper.INSTANCE::itemEntityToItem);
    }
}