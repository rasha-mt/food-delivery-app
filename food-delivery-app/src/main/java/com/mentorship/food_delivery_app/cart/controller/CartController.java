package com.mentorship.food_delivery_app.cart.controller;

import com.mentorship.food_delivery_app.cart.dto.Requests.CartItemRequest;
import com.mentorship.food_delivery_app.cart.dto.CartDto;
import com.mentorship.food_delivery_app.cart.service.contract.CartService;
import com.mentorship.food_delivery_app.common.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart() {
        CartDto response = cartService.createCart();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartDto>> viewCart() {
        // Adding Customer manually for testing
        CartDto response = cartService.viewCart();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        response,
                        "Cart created successfully"
                )
        );
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@RequestBody @Valid CartItemRequest request) {
        // Adding Customer manually for testing
        cartService.addItem(request.getMenuItemId(), request.getQuantity());

        return ResponseEntity.ok("successfully added item");
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();

    }

}