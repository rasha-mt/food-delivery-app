package com.mentorship.ecommerce_app.cart.dto;

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
    private int quantity;
}
