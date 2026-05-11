package com.mentorship.food_delivery_app.common.exceptions;

import org.springframework.http.HttpStatus;

public abstract class DomainException extends ApiException {

    protected DomainException(HttpStatus status, String errorCode, String message) {
        super(status, errorCode, message);
    }
}