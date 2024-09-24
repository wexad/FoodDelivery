package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.OrderItemDTO;
import com.wexad.BurgerHub.model.OrderItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper ORDER_ITEM_MAPPER = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "productId")
    @Mapping(source = "product.image.path", target = "imagePath")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.compound.weight", target = "weight")
    @Mapping(source = "count", target = "count")
    OrderItemDTO toDto(OrderItem orderItem);

    @InheritInverseConfiguration
    OrderItem toEntity(OrderItemDTO orderItemDTO);

    List<OrderItemDTO> toDtoList(List<OrderItem> orderItemList);

    List<OrderItem> toEntityList(List<OrderItemDTO> orderItemDTOList);
}
