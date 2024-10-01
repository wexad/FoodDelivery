package com.wexad.BurgerHub.dto;

public record OrderItemDTO(Long id, Long productId, Double price, String productName, Double weight, Long count,
                           String imagePath) {
}
