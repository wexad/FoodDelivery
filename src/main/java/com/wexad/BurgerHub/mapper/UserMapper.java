package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.AuthUserDTO;
import com.wexad.BurgerHub.dto.RoleDTO;
import com.wexad.BurgerHub.dto.UserDTO;
import com.wexad.BurgerHub.enums.RoleName;
import com.wexad.BurgerHub.model.AuthUser;
import com.wexad.BurgerHub.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);


    AuthUser toEntity(AuthUserDTO user);

    AuthUserDTO toDto(AuthUser user);

    AuthUser toEntity(UserDTO user);

    static UserDTO toUserDto(AuthUser user) {
        Set<RoleDTO> roleDTOs = user.getRoles().stream()
                .map(role -> new RoleDTO(role.getName().toString(), role.getDescription()))
                .collect(Collectors.toSet());

        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), roleDTOs, user.isDeleted());
    }


    List<UserDTO> toUserDtoList(List<AuthUser> users);

    List<AuthUserDTO> toDto(List<AuthUser> users);

    default Role getDefaultRole() {
        Role defaultRole = new Role();
        defaultRole.setName(RoleName.USER);
        return defaultRole;
    }

}
