package com.mentorship.food_delivery_app.cart.exceptions;

import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class CartNotFoundException extends CartDomainException {

    public CartNotFoundException() {
        super(
                ErrorMessage.CART_NOT_FOUND.getErrorMessage(),
                HttpStatus.NOT_FOUND,
                ErrorMessage.CART_NOT_FOUND.getErrorCode()
        );
    }

}

