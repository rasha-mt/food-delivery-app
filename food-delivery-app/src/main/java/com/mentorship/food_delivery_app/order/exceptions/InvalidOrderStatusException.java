package com.mentorship.food_delivery_app.order.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidOrderStatusException
        extends OrderDomainException {

    public InvalidOrderStatusException() {
        super(
                HttpStatus.BAD_REQUEST,
                "INVALID_ORDER_STATUS",
                "Invalid order status"
        );
    }
}
