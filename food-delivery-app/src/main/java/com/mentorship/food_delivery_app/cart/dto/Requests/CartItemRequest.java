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

    @NotNull(message = "Menu item ID is required")
    private UUID menuItemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 30, message = "Quantity cannot exceed 30")
    private int quantity;
}
