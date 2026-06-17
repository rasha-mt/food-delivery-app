package com.mentorship.food_delivery_app.order.repository;

import com.mentorship.food_delivery_app.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByCustomerId(UUID customerId);

    List<Order> findByRestaurantId(UUID restaurantId);

    @EntityGraph(attributePaths = {"customer"})
    Page<Order> findByCustomerId(
            UUID customerId,
            Pageable pageable
    );
}