package com.wexad.BurgerHub.dto;

public record OrderItemDTO(Long productId, String productName, Double weight, Long count, String imagePath) {
}
