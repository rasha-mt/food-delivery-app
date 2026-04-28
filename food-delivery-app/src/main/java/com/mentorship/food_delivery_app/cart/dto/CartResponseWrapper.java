package com.mentorship.food_delivery_app.cart.dto;

import lombok.Getter;

@Getter
public class CartResponseWrapper {
    private CartResponse cartResponse;
    private boolean created;

    public CartResponseWrapper(CartResponse cartResponse, boolean created) {
        this.cartResponse = cartResponse;
        this.created = created;
    }

}