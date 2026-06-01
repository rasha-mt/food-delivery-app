package com.mentorship.food_delivery_app.order.mapper;

import com.mentorship.food_delivery_app.order.dto.OrderDto;
import com.mentorship.food_delivery_app.order.dto.OrderItemsDto;
import com.mentorship.food_delivery_app.order.model.Order;
import com.mentorship.food_delivery_app.order.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderDto toDto(Order order) {

        return new OrderDto(
                order.getId(),
                order.getCustomer().getId(),
                order.getRestaurantId(),
                order.getStatus(),
                order.getTotalPrice(),
                mapItems(order.getItems()),
                order.getCreatedAt()
        );
    }

    private List<OrderItemsDto> mapItems(List<OrderItem> items) {

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