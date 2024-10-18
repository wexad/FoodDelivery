package com.wexad.BurgerHub.dto;

public record CategoryDTO(Long id, String categoryName, String description, ImageDTO image, Boolean deleted) {
}
