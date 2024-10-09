package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.UserAddressDTO;
import com.wexad.BurgerHub.model.AuthUser;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    UserAddressMapper USER_ADDRESS_MAPPER = Mappers.getMapper(UserAddressMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "deleted", target = "deleted")
    @Mapping(source = "address", target = "address")
    UserAddressDTO toDTO(AuthUser authUser);

    @InheritInverseConfiguration
    AuthUser toAuthUser(UserAddressDTO userAddressDTO);

    List<UserAddressDTO> toDTO(List<AuthUser> authUser);

    List<AuthUser> toAuthUser(List<UserAddressDTO> userAddressDTO);

}
