package com.wexad.BurgerHub.dto;

import java.util.List;

public record LoginDTOWithoutOrderItems(Tokens tokens, List<CategoryDataDTO> categories,
                                        List<ProductDataDTO> products) {
}
