package com.mentorship.food_delivery_app.restaurant.exceptions;

import com.mentorship.food_delivery_app.common.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public abstract class RestaurantDomainException extends DomainException {

    protected RestaurantDomainException(
            HttpStatus status,
            String errorCode,
            String message
    ) {
        super(status, errorCode, message);
    }
}