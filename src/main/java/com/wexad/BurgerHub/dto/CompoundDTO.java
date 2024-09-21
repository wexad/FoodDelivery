package com.wexad.BurgerHub.dto;

import com.wexad.BurgerHub.enums.Size;

public record CompoundDTO(Double weight, Size size, Long calories, Long fat, Long protein) {
}
