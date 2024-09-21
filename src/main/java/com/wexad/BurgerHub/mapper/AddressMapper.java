package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.AddressDTO;
import com.wexad.BurgerHub.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {
    AddressMapper ADDRESS_MAPPER = Mappers.getMapper(AddressMapper.class);

    AddressDTO toDTO(Address address);

    Address toEntity(AddressDTO addressDTO);
}
