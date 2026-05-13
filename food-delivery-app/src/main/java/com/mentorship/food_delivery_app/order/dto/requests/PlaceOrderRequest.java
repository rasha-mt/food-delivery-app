package com.mentorship.food_delivery_app.order.dto.requests;

import java.util.List;
import java.util.UUID;

public record PlaceOrderRequest(
        UUID restaurantId,
        List<OrderItemRequest> items
) {}