package com.wexad.BurgerHub.dto;

import java.util.List;

public record LoginDTO(Tokens tokens, List<CategoryDataDTO> categories, List<ProductDataDTO> products,
                       List<OrderItemDTO> orderItems) {
}
