package com.mentorship.food_delivery_app.restaurant.exceptions;

import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;


public class MenuItemNotFoundException extends RestaurantDomainException {

        public MenuItemNotFoundException() {
            super(
                    HttpStatus.NOT_FOUND,
                    ErrorMessage.MENU_ITEM_NOT_FOUND.getErrorCode(),
                    ErrorMessage.MENU_ITEM_NOT_FOUND.getErrorMessage()
            );
        }
    }