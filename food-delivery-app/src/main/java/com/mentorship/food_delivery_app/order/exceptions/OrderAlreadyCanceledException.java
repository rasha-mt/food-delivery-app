package com.mentorship.food_delivery_app.order.exceptions;

import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class OrderAlreadyCanceledException extends OrderDomainException {
    public OrderAlreadyCanceledException() {
        super(
                HttpStatus.CONFLICT,
                ErrorMessage.ORDER_ALREADY_CANCELED_ERROR.getErrorCode(),
                ErrorMessage.ORDER_ALREADY_CANCELED_ERROR.getErrorMessage());
    }
}
