package com.mentorship.food_delivery_app.cart.controller;

import com.mentorship.food_delivery_app.cart.dto.Requests.CartItemRequest;
import com.mentorship.food_delivery_app.cart.dto.CartDto;
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

    @PostMapping
    public ResponseEntity<CartDto> createCart() {
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        ;// getCustomerIdFromToken(); // extracted from JWT

        CartDto response = cartService.createCart(customerId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CartDto> viewCart() {
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        ;// getCustomerIdFromToken(); // extracted from JWT

        CartDto response = cartService.viewCart(customerId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody @Valid CartItemRequest item) {
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        ;// getCustomerIdFromToken(); // extracted from JWT
        cartService.addItem(item.getMenuItemId(), item.getQuantity(), customerId);

        return ResponseEntity.ok("successfully added item");
    }

    @DeleteMapping("/clear/{cartId}")
    public ResponseEntity<?> clearCart(@PathVariable UUID CartID) {
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        // getCustomerIdFromToken(); // extracted from JWT

        cartService.clearCart(CartID,customerId);
        return ResponseEntity.noContent().build();
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