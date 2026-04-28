package com.mentorship.food_delivery_app.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CartItemView {

    private String itemName;
    private int quantity;
    private String description;
    private BigDecimal unitPrice;

}
