package com.mentorship.food_delivery_app.order.event;

import com.mentorship.food_delivery_app.order.model.Order;

public record OrderCanceledEvent(Order order) {}
