package com.mentorship.food_delivery_app.common.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {

        CART_NOT_FOUND("Cart is not found, please create cart first."),
        MENU_ITEM_NOT_FOUND("Item is not available, please choose another item."),
        CART_IS_LOCKED ("Please complete checkout first."),
        VALIDATION_ERROR("Validation error.");

        private final String errorMessage;

        ErrorMessage(String errorMessage){
            this.errorMessage = errorMessage;
        }
}
