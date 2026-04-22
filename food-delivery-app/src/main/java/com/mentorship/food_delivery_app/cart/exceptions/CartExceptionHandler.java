package com.mentorship.ecommerce_app.cart.exceptions;

import com.mentorship.ecommerce_app.cart.dto.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartExceptionHandler {

  @ExceptionHandler(CartNotFoundException.class)
  public ResponseEntity<Status> handleCartNotFound(CartNotFoundException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new Status("404", ex.getMessage()));
  }

  @ExceptionHandler(MenuItemNotFoundException.class)
  public ResponseEntity<Status> handleMenuItemNotFound(MenuItemNotFoundException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new Status("404", ex.getMessage()));
  }

  @ExceptionHandler(CartLockedException.class)
  public ResponseEntity<Status> handleCartLocked(CartLockedException ex) {
    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new Status("409", ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Status> handleGeneric(Exception ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new Status("500", "Unexpected error occurred"));
  }
}
