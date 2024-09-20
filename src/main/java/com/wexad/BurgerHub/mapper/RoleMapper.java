package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.RoleDTO;
import com.wexad.BurgerHub.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper ROLE_MAPPER = Mappers.getMapper(RoleMapper.class);

    RoleDTO ToDTO(Role role);
}
