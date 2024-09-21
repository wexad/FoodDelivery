package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.ProductOrderDTO;
import com.wexad.BurgerHub.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductOrderMapper {

    @Mapping(source = "product.id", target = "id")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.price", target = "price")
    @Mapping(source = "count", target = "count")
    ProductOrderDTO toDto(Product product, Long count);
}


