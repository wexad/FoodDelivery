package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.ProductDataDTO;
import com.wexad.BurgerHub.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductDataMapper {
    ProductDataMapper PRODUCT_DATA_MAPPER = Mappers.getMapper(ProductDataMapper.class);

    @Mapping(source = "category.id", target = "categoryDataDTO.id")
    @Mapping(source = "category.categoryName", target = "categoryDataDTO.name") // Ensure this is mapped
    @Mapping(source = "category.image.path", target = "categoryDataDTO.path") // Ensure this is mapped
    @Mapping(source = "image.path", target = "imageDTO.path")
    @Mapping(source = "compound.weight", target = "weight")
    ProductDataDTO toDto(Product product);

    List<ProductDataDTO> toDtoList(List<Product> products);
}
