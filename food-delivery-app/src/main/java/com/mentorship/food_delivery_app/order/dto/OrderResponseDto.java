package com.mentorship.food_delivery_app.order.dto;

import com.mentorship.food_delivery_app.order.enums.OrderStatus;
import com.mentorship.food_delivery_app.order.model.Order;
import com.mentorship.food_delivery_app.order.model.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDto(

        UUID orderId,

        UUID customerId,

        UUID restaurantId,

        OrderStatus status,

        BigDecimal totalPrice,

        List<OrderItemsDto> items,

        LocalDateTime createdAt
) {
    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getCustomer().getId(),
                order.getRestaurantId(),
                order.getStatus(),
                order.getTotalPrice(),
                mapItems(order.getItems()),
                order.getCreatedAt()
        );
    }

    private static List<OrderItemsDto> mapItems(List<OrderItem> items) {

        return items.stream()
                .map(item -> new OrderItemsDto(
                        item.getMenuItemId(),
                        item.getMenuItemName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubTotal()
                ))
                .toList();
    }
}
