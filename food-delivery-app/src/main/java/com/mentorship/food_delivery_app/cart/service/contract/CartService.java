package com.mentorship.food_delivery_app.cart.service.contract;


import com.mentorship.food_delivery_app.cart.dto.CartDto;
import com.mentorship.food_delivery_app.cart.model.Cart;

import java.util.UUID;

public interface CartService {

    // CREATE CART
    CartDto createCart(UUID customerId);

    // VIEW CART
    CartDto viewCart(UUID customerId);

    // ADD ITEM
    void addItem(UUID itemId, int quantity, UUID customerId);

    // CLEAR CART
    void clearCart(UUID cartId, UUID customerId);


}
