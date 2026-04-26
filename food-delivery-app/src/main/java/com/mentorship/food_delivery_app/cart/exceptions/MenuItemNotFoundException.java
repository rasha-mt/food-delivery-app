package com.mentorship.food_delivery_app.cart.exceptions;

import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class MenuItemNotFoundException extends CartDomainException {

    public MenuItemNotFoundException(String message) {
        super(message,HttpStatus.NOT_FOUND, ErrorMessage.MENU_ITEM_NOT_FOUND.getErrorMessage());
    }
    public MenuItemNotFoundException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND, ErrorMessage.MENU_ITEM_NOT_FOUND.getErrorMessage());
    }
}