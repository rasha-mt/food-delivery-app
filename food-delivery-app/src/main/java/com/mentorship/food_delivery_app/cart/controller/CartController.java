package com.mentorship.food_delivery_app.cart.controller;

import com.mentorship.food_delivery_app.cart.dto.*;
import com.mentorship.food_delivery_app.cart.service.contract.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<CartResponse> createCart() {
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        ;// getCustomerIdFromToken(); // extracted from JWT

        CartResponseWrapper response = cartService.createCart(customerId);

        return response.isCreated()
                ? ResponseEntity.status(HttpStatus.CREATED).body(response.getCartResponse())
                : ResponseEntity.ok(response.getCartResponse());
    }

    @GetMapping("/me")
    public ResponseEntity<CartResponse> viewCart() {
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        ;// getCustomerIdFromToken(); // extracted from JWT

        CartResponse response = cartService.viewCart(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<AddCartResponse> addItem(@RequestBody @Valid CartItemRequest item) {
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        ;// getCustomerIdFromToken(); // extracted from JWT
        AddCartResponse response = cartService.addItem(item.getMenuItemId(), item.getQuantity(), customerId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ClearCartResponse> clearCart() {
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        // getCustomerIdFromToken(); // extracted from JWT
        ClearCartResponse response = cartService.clearCart(customerId);
        return ResponseEntity.ok(response);
    }

    /*
     * private UUID getCustomerIdFromToken() {
      return UUID.fromString(
          SecurityContextHolder.getContext()
          .getAuthentication()
          .getName()
        );
     }
     */
}