package com.wexad.BurgerHub.dto;

public record RestaurantDTO(Long id, String name, String contactNumber, AddressDTO addressDTO, Boolean deleted) {
}
