package com.mentorship.food_delivery_app.order.exceptions;

import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends OrderDomainException {
    public OrderNotFoundException() {
        super(
                HttpStatus.NOT_FOUND,
                ErrorMessage.ORDER_NOT_FOUND_ERROR.getErrorCode(),
                ErrorMessage.ORDER_NOT_FOUND_ERROR.getErrorMessage());
    }
}
