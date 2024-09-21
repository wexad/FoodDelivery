package com.wexad.BurgerHub.dto;

public record ProductDataDTO(Long id, CategoryDataDTO categoryDataDTO, Double price, String description,
                             ImageDTO imageDTO, Double weight) {
}