package com.wexad.BurgerHub.dto;

public record ProductDTO(
        Long id,
        String name,
                         String description,
                         Double price,
                         ImageDTO image,
                         CompoundDTO compound,
                         boolean deleted) {
}
