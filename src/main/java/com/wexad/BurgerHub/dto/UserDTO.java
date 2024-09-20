package com.wexad.BurgerHub.dto;

import com.wexad.BurgerHub.model.Role;

import java.util.Set;

public record UserDTO(Long id, String username, String email, Set<RoleDTO> roles, Boolean deleted) {
}
