package com.mentorship.food_delivery_app.customer.controller;

import com.mentorship.food_delivery_app.order.dto.OrderResponseDto;
import com.mentorship.food_delivery_app.order.dto.PagedResponse;
import com.mentorship.food_delivery_app.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public PagedResponse<OrderResponseDto> getMyOrders(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return orderService.getMyOrdersHistory(page, size);
    }

}
