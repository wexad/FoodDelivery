package com.wexad.BurgerHub.dto;

public record ProductDTO(String name,
                         String description,
                         Double price,
                         ImageDTO image,
                         CompoundDTO compound,
                         boolean deleted) {
}
