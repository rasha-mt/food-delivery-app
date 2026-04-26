package com.mentorship.food_delivery_app.cart.exceptions;

import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;


public class CartLockedException extends CartDomainException {
        public CartLockedException(String message) {
            super(message,HttpStatus.CONFLICT, ErrorMessage.CART_IS_LOCKED.getErrorMessage());
        }
        public CartLockedException(String message, Throwable cause) {
        super(message, cause, HttpStatus.CONFLICT, ErrorMessage.CART_IS_LOCKED.getErrorMessage());
    }

    }

