package com.mentorship.food_delivery_app.order.exceptions;

import com.mentorship.food_delivery_app.common.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class OrderDomainException extends DomainException {
    public OrderDomainException(HttpStatus status, String code, String message) {
        super(status, code, message);
    }
}
