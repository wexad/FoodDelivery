package com.wexad.BurgerHub.dto;

public record RestaurantDTO(String name, String contactNumber, AddressDTO addressDTO, Boolean deleted) {
}
