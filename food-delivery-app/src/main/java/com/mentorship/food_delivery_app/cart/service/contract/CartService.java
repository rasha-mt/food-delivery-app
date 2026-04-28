package com.mentorship.food_delivery_app.cart.service.contract;


import com.mentorship.food_delivery_app.cart.dto.AddCartResponse;
import com.mentorship.food_delivery_app.cart.dto.CartResponse;
import com.mentorship.food_delivery_app.cart.dto.CartResponseWrapper;
import com.mentorship.food_delivery_app.cart.dto.ClearCartResponse;

import java.util.UUID;

public interface CartService {

    // CREATE CART
     CartResponseWrapper createCart(UUID customerId) ;


    // VIEW CART
     CartResponse viewCart(UUID customerId);

    // ADD ITEM
     AddCartResponse addItem(UUID itemId, int quantity, UUID customerId) ;

    // CLEAR CART
     ClearCartResponse clearCart(UUID customerId);



}
