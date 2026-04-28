package com.mentorship.food_delivery_app.cart.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CartDomainException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String errorCode;

    protected CartDomainException(String message,
                                  HttpStatus httpStatus,
                                  String errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}

