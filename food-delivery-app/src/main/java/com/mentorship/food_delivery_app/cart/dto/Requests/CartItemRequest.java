package com.mentorship.food_delivery_app.cart.dto.Requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CartItemRequest {
    @NotNull
    private UUID  menuItemId;
    @Min(1)
    @Max(30)
    private int quantity;
}
