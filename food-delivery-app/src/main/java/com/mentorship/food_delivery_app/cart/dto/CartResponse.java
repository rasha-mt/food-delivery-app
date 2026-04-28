package com.mentorship.food_delivery_app.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CartResponse {

    private UUID cartId;
    private List<CartItemView> items;
    private BigDecimal totalPrice;

}