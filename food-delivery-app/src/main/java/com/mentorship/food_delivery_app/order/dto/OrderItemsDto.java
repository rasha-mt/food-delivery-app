package com.mentorship.food_delivery_app.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemsDto(

        UUID menuItemId,

        String menuItemName,

        Integer quantity,

        BigDecimal unitPrice,

        BigDecimal subTotal

) {
}
