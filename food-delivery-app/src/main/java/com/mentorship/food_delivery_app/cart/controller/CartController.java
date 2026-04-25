package com.mentorship.food_delivery_app.cart.controller;
import com.mentorship.food_delivery_app.cart.dto.AddCartResponse;
import com.mentorship.food_delivery_app.cart.dto.CartItemRequest;
import com.mentorship.food_delivery_app.cart.dto.CartResponse;
import com.mentorship.food_delivery_app.cart.dto.ClearCartResponse;
import com.mentorship.food_delivery_app.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");;//getCustomerIdFromToken(); // extracted from JWT

        CartResponse response = cartService.createCart(customerId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<CartResponse> viewCart(){
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");;//getCustomerIdFromToken(); // extracted from JWT

        CartResponse response = cartService.viewCart(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/add")
   public ResponseEntity<AddCartResponse> addItem(@RequestBody @Valid CartItemRequest item){
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");;//getCustomerIdFromToken(); // extracted from JWT
        AddCartResponse response = cartService.addItem(item.getMenuItemId(),item.getQuantity(),customerId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ClearCartResponse> clearCart(){
        // Adding Customer manually for testing
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");;//getCustomerIdFromToken(); // extracted from JWT
        ClearCartResponse response = cartService.clearCart(customerId);
        return ResponseEntity.ok(response);
    }

/*
    private UUID getCustomerIdFromToken() {
        return UUID.fromString(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );
    }*/
}