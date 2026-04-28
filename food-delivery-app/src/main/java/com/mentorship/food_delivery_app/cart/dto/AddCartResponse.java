package com.mentorship.food_delivery_app.cart.dto;

import com.mentorship.food_delivery_app.common.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCartResponse {
    private StatusDto status;
}
