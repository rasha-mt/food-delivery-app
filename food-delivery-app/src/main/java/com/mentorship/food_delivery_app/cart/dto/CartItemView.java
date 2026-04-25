package com.mentorship.food_delivery_app.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartItemView {

    private String itemName;
    private int quantity;
    private String description;
    private double unitPrice;

}
