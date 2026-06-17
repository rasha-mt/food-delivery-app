package com.mentorship.food_delivery_app.customer.controller;

import com.mentorship.food_delivery_app.common.responses.ApiResponse;
import com.mentorship.food_delivery_app.customer.dto.requests.addPreferredPaymentRequest;
import com.mentorship.food_delivery_app.customer.service.CustomerService;
import com.mentorship.food_delivery_app.order.dto.OrderResponseDto;
import com.mentorship.food_delivery_app.order.dto.PagedResponse;
import com.mentorship.food_delivery_app.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {
    private final OrderService orderService;
    private final CustomerService customerService;

    @GetMapping("/orders-history")
    public PagedResponse<OrderResponseDto> getMyOrders(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return orderService.getMyOrdersHistory(page, size);
    }

    @PostMapping("/preferred-payment")
    public ResponseEntity<ApiResponse<?>> addPreferredPayment(
            @RequestHeader("Authorization") String token,
            @RequestParam addPreferredPaymentRequest  request
            ) {

         customerService.addCustomerPreferredPaymentType(request.getPayment_type_id());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        null,
                        "address is updated successfully"
                )
        );
    }

    @PatchMapping("/deactivate-account")
    public ResponseEntity<ApiResponse<?>> deactivateAccount(
            @RequestHeader("Authorization") String token
    ) {

        customerService.deactivateAccount();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        null,
                        "account has been deactivated successfully"
                )
        );
    }

}
