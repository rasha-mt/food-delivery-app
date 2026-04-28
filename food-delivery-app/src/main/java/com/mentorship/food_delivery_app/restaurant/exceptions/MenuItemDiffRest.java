package com.mentorship.food_delivery_app.restaurant.exceptions;

import com.mentorship.food_delivery_app.cart.exceptions.CartDomainException;
import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class MenuItemDiffRest extends CartDomainException {
  public MenuItemDiffRest() {
    super(
            ErrorMessage.DIFFERENT_RESTRAUNT_ERROR.getErrorMessage(),
            HttpStatus.CONFLICT,
            ErrorMessage.DIFFERENT_RESTRAUNT_ERROR.getErrorCode()
    );
  }

}


