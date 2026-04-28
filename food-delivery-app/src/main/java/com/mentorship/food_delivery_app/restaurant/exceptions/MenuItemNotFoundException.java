package com.mentorship.food_delivery_app.restaurant.exceptions;

import com.mentorship.food_delivery_app.cart.exceptions.CartDomainException;
import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;


public class MenuItemNotFoundException extends CartDomainException {

        public MenuItemNotFoundException() {
            super(
                    ErrorMessage.MENU_ITEM_NOT_FOUND.getErrorMessage(),
                    HttpStatus.NOT_FOUND,
                    ErrorMessage.MENU_ITEM_NOT_FOUND.getErrorCode()
            );
        }
    }