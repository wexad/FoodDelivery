package com.wexad.BurgerHub.dto;

public record OrderItemDTO(Long productId,Long price, String productName, Double weight, Long count, String imagePath) {
}
