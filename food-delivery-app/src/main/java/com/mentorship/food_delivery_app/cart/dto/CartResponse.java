package com.mentorship.ecommerce_app.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CartResponse {

    private UUID cartId;
    private List<CartItemView> items;
    private Double totalPrice;

}