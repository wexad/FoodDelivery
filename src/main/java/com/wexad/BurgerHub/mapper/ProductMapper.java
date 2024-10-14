package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.ProductDTO;
import com.wexad.BurgerHub.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toEntity(ProductDTO productDTO);

    ProductDTO toDTO(Product product);

    List<Product> toEntity(List<ProductDTO> productDTOList);

    List<ProductDTO> toDTO(List<Product> productList);
}
