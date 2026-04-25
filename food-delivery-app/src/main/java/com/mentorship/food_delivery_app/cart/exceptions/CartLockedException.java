package com.mentorship.food_delivery_app.cart.exceptions;

public class CartLockedException extends RuntimeException {
        public CartLockedException(String message) {
            super(message);
        }
    }

