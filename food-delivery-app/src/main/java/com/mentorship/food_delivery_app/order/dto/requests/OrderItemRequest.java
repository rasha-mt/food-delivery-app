package com.mentorship.food_delivery_app.order.dto.requests;

import java.util.UUID;

public record OrderItemRequest(
        UUID menuItemId,
        int quantity
) {}