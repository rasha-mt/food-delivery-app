package com.mentorship.food_delivery_app.cart.dto;

import com.mentorship.food_delivery_app.cart.model.CartItem;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemDto(
        UUID menuItemId,
        String menuItemName,
        Integer quantity,
        BigDecimal subTotal,
        String note
) { }
