package com.mentorship.food_delivery_app.common.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {

        CART_NOT_FOUND("CART_NOT_FOUND","Cart is not found, please create cart first."),
        MENU_ITEM_NOT_FOUND("MENU_ITEM_NOT_FOUND","Item is not available, please choose another item."),
        CART_IS_LOCKED ("CART_IS_LOCKED","Please complete checkout first."),
        DIFFERENT_RESTRAUNT_ERROR("DIFFERENT_RESTRAUNT_ERROR","Cannot mix items from different restaurants"),
        VALIDATION_ERROR("VALIDATION_ERROR","Validation error."),
        ORDER_NOT_FOUND_ERROR("ORDER_NOT_FOUND","Order not found"),
        ORDER_ALREADY_CANCELED_ERROR("ORDER_ALREADY_CANCELED","Order already canceled"),;


        private final String errorCode;
        private final String errorMessage;

        ErrorMessage(String errorCode, String errorMessage) {
                this.errorCode = errorCode;
                this.errorMessage = errorMessage;
        }
}
