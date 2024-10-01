package com.wexad.BurgerHub.dto;

import java.util.List;

public record BasketDTO(List<OrderItemDTO> orderItemDTOS, Long count, Double price) {
}
