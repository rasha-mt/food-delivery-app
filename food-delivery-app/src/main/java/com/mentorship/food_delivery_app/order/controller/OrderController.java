package com.mentorship.food_delivery_app.order.controller;

import com.mentorship.food_delivery_app.common.responses.ApiResponse;
import com.mentorship.food_delivery_app.customer.model.Customer;
import com.mentorship.food_delivery_app.customer.service.CustomerService;
import com.mentorship.food_delivery_app.order.dto.OrderResponseDto;
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
    private final CustomerService customerService;

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
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getCustomerOrders() {
        Customer customer = customerService.getCustomer();
        List<OrderResponseDto> orders = orderService.getCustomerOrders(customer);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        orders,
                        null
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrderDetails(@PathVariable UUID orderId) {
        OrderResponseDto orderDetails = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                orderDetails,
                null
        ));
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<ApiResponse<OrderResponseDto>> acceptOrder(
            @PathVariable UUID id
    ) {

        OrderResponseDto response =
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
    public ResponseEntity<ApiResponse<OrderResponseDto>> cancelOrder(
            @PathVariable UUID id
    ) {

        OrderResponseDto response =
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
