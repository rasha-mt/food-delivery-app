package com.mentorship.food_delivery_app.order.dto;

import java.math.BigDecimal;

public record OrderSummaryDto(
        long totalOrders,
        long delivered,
        long canceled,
        BigDecimal totalRevenue
) {}