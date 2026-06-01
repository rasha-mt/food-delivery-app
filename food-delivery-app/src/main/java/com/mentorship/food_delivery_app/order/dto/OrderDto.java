package com.mentorship.food_delivery_app.order.dto;

import com.mentorship.food_delivery_app.order.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDto(

        UUID orderId,

        UUID customerId,

        UUID restaurantId,

        OrderStatus status,

        BigDecimal totalPrice,

        List<OrderItemsDto> items,

        LocalDateTime createdAt
) {
}
