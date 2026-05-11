package com.mentorship.food_delivery_app.cart.service.contract;

import com.mentorship.food_delivery_app.cart.dto.CartDto;

import java.util.UUID;

public interface CartService {

    // CREATE CART
    CartDto createCart();

    // VIEW CART
    CartDto viewCart();

    // ADD ITEM
    void addItem(UUID itemId, int quantity);

    // CLEAR CART
    void clearCart(UUID cartId);


}
