package com.github.springprac.respository.items;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ElectroniceStoreItemJpaRepository extends JpaRepository<ItemEntity,Integer> {  // JpaRepositry<> 내부에는 첫째는 Entity명이 들어가고 , 두번재에는 Entity의 타입이 들어가야함
    List<ItemEntity> findItemEntitiesByTypeIn(List<String> types);

    List<ItemEntity> findItemEntitiesByPriceLessThanEqualOrderByPriceAsc(Integer maxValue );

    Page<ItemEntity> findAllByTypeIn(List<String> types, Pageable pageable);
}


