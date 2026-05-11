package com.mentorship.food_delivery_app.restaurant.exceptions;

import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class MenuItemDiffRest extends RestaurantDomainException {
  public MenuItemDiffRest() {
    super(
            HttpStatus.CONFLICT,
            ErrorMessage.DIFFERENT_RESTRAUNT_ERROR.getErrorCode(),
            ErrorMessage.DIFFERENT_RESTRAUNT_ERROR.getErrorMessage()
    );
  }

}


