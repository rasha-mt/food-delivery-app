package com.mentorship.food_delivery_app.restaurant.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemDto(
        UUID id,
        String name,
        BigDecimal price,
        String description
) { }
