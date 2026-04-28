package com.mentorship.food_delivery_app.common.enums;

import lombok.Getter;

@Getter
public enum SuccessMessage {


    ITEM_ADDED("Item has been added successfully."),
    CART_CLEARED("Cart has been cleared successfully.");


    private final String successMessage;

    SuccessMessage(String successMessage){
        this.successMessage = successMessage;
    }
}

