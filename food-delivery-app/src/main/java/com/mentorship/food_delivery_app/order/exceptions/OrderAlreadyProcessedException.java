package com.mentorship.food_delivery_app.order.exceptions;

import org.springframework.http.HttpStatus;

public class OrderAlreadyProcessedException
            extends OrderDomainException {

    public OrderAlreadyProcessedException() {
        super(
                HttpStatus.CONFLICT,
                "ORDER_ALREADY_PROCESSED",
                "Order already processed"
        );
    }
}
