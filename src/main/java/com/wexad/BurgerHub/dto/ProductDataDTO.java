package com.wexad.BurgerHub.dto;

public record ProductDataDTO(Long id, Double price, String name,
                             ImageDTO imageDTO, Double weight) {
}