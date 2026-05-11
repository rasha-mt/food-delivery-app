package com.mentorship.food_delivery_app.cart.mapper;

import com.mentorship.food_delivery_app.cart.dto.CartItemDto;
import com.mentorship.food_delivery_app.cart.model.CartItem;

public class CartMapper {

    public static CartItemDto toDto(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getMenuItem().getId(),
                cartItem.getMenuItem().getMenuItemName(),
                cartItem.getCartItemQuantity(),
                cartItem.getTotalPrice(),
                cartItem.getCartItemNote()
        );
    }
}
