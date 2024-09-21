package com.wexad.BurgerHub.dto;

public record UserDataDTO(String username,
                          String email,
                          Boolean isAdmin,
                          AddressDTO address,
                          ImageDTO image) {
}
