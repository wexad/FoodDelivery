package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.AuthUserDTO;
import com.wexad.BurgerHub.enums.RoleName;
import com.wexad.BurgerHub.model.AuthUser;
import com.wexad.BurgerHub.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);


    AuthUser toEntity(AuthUserDTO user);

    AuthUserDTO toDto(AuthUser user);


    default Role getDefaultRole() {
        Role defaultRole = new Role();
        defaultRole.setName(RoleName.USER);
        return defaultRole;
    }

}
