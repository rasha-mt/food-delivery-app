package com.mentorship.food_delivery_app.cart.dto;

import com.mentorship.food_delivery_app.common.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClearCartResponse {
    private StatusDto status;
}
