package com.wexad.BurgerHub.dto;

public record UserDataDTO(
        Long id,
        String username,
        String email,
        Boolean isAdmin,
        AddressDTO address,
        ImageDTO image) {
}
