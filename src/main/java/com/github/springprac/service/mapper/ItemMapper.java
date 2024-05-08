package com.github.springprac.service.mapper;


import com.github.springprac.respository.items.ItemEntity;
import com.github.springprac.web.dto.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    // 싱글톤
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    // 메소드
    @Mapping(target = "spec.cpu",source = "cpu")
    @Mapping(target = "spec.capacity",source = "capacity")
    Item itemEntityToItem(ItemEntity itemEntity);

}
