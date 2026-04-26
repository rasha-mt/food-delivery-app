package com.mentorship.food_delivery_app.cart.exceptions;

import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class CartNotFoundException extends CartDomainException {

    public CartNotFoundException(String message) {
        super(message,HttpStatus.NOT_FOUND, ErrorMessage.CART_NOT_FOUND.getErrorMessage());
    }
    public CartNotFoundException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND, ErrorMessage.CART_NOT_FOUND.getErrorMessage());
    }
}