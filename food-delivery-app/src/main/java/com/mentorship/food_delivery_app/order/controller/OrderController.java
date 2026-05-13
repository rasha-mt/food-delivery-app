package com.mentorship.food_delivery_app.order.controller;

import com.mentorship.food_delivery_app.common.responses.ApiResponse;
import com.mentorship.food_delivery_app.order.dto.OrderDto;
import com.mentorship.food_delivery_app.order.dto.requests.PlaceOrderRequest;
import com.mentorship.food_delivery_app.order.enums.OrderStatus;
import com.mentorship.food_delivery_app.order.model.Order;
import com.mentorship.food_delivery_app.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(
            @RequestBody PlaceOrderRequest request
    ) {
        return ResponseEntity.ok(orderService.placeOrder( request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Order>> updateStatus(
            @PathVariable UUID id,
            @RequestParam OrderStatus status
    ) {
        Order response= orderService.updateStatus(id, status);
        return ResponseEntity.ok(    new ApiResponse<>(
                200,
                response,
                null
        ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getCustomerOrders() {
        List<OrderDto> orders = orderService.getCustomerOrders();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        orders,
                        null
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderDetails(@PathVariable UUID orderId) {
        OrderDto orderDetails = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                orderDetails,
                null
        ));
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<ApiResponse<OrderDto>> acceptOrder(
            @PathVariable UUID id
    ) {

        OrderDto response =
                orderService.acceptOrder(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        response,
                        "Order accepted successfully"
                )
        );
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderDto>> cancelOrder(
            @PathVariable UUID id
    ) {

        OrderDto response =
                orderService.cancelOrder(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        response,
                        "Order canceled successfully"
                )
        );
    }
}
