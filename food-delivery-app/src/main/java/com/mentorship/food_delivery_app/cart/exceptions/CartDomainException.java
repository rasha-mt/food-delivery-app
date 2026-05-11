package com.mentorship.food_delivery_app.cart.exceptions;

import com.mentorship.food_delivery_app.common.exceptions.DomainException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CartDomainException extends DomainException {

    protected CartDomainException(HttpStatus status, String errorCode, String message) {
        super(status, errorCode, message);
    }
}

