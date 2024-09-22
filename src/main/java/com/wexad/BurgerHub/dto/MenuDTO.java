package com.wexad.BurgerHub.dto;

import java.util.List;

public record MenuDTO(List<CategoryDataDTO> categories, List<ProductDataDTO> productsByCategoryId) {
}
