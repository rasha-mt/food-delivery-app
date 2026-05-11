package com.mentorship.food_delivery_app.cart.exceptions;

import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;


public class CartLockedException extends CartDomainException {
        public CartLockedException() {

            super(
                        HttpStatus.CONFLICT,
                        ErrorMessage.CART_IS_LOCKED.getErrorCode(),
                        ErrorMessage.CART_IS_LOCKED.getErrorMessage()
                );

        }


    }



