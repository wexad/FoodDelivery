package com.wexad.BurgerHub.dto;

public record OrderRequestDTO(ProductOrder[] orders, Long cardNumber) {
}
