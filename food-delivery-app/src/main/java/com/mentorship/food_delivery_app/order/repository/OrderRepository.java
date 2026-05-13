package com.mentorship.food_delivery_app.order.repository;

import com.mentorship.food_delivery_app.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByCustomerId(UUID customerId);

    List<Order> findByRestaurantId(UUID restaurantId);
}