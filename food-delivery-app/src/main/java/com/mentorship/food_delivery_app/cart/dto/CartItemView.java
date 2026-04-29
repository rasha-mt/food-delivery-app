package com.mentorship.food_delivery_app.cart.dto;

import com.mentorship.food_delivery_app.cart.entity.CartItem;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemView(
        UUID menuItemId,
        String menuItemName,
        Integer quantity,
        BigDecimal subTotal,
        String note
) {

    public static CartItemView fromCartItem(CartItem cartItem) {
        return new CartItemView(
                cartItem.getMenuItem().getMenuItemId(),
                cartItem.getMenuItem().getMenuItemName(),
                cartItem.getCartItemQuantity(),
                cartItem.getTotalPrice(),
                cartItem.getCartItemNote()
        );
    }
}
