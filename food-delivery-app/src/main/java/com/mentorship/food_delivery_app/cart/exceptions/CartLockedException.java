package com.mentorship.ecommerce_app.cart.exceptions;

public class CartLockedException extends RuntimeException {
        public CartLockedException(String message) {
            super(message);
        }
    }

