package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.AddressDTO;
import com.wexad.BurgerHub.dto.RestaurantDTO;
import com.wexad.BurgerHub.model.Address;
import com.wexad.BurgerHub.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RestaurantMapper {

    RestaurantMapper RESTAURANT_MAPPER = Mappers.getMapper(RestaurantMapper.class);

    @Mapping(source = "addressDTO", target = "address")
    Restaurant toEntity(RestaurantDTO restaurantDTO);

    @Mapping(source = "address", target = "addressDTO")
    RestaurantDTO toDto(Restaurant restaurant);

    List<Restaurant> toEntity(List<RestaurantDTO> restaurantDTOList);
    List<RestaurantDTO> toDto(List<Restaurant> restaurantList);
    Address toEntity(AddressDTO addressDTO);

    AddressDTO toDto(Address address);
}

