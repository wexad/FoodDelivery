package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.UserDataDTO;
import com.wexad.BurgerHub.enums.RoleName;
import com.wexad.BurgerHub.model.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDataMapper {

    UserDataMapper USER_DATA_MAPPER = Mappers.getMapper(UserDataMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "isAdmin", expression = "java(isAdmin(authUser))")
    UserDataDTO toUserDataDTO(AuthUser authUser);

    default Boolean isAdmin(AuthUser authUser) {
        return authUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals(RoleName.ADMIN));
    }
}
