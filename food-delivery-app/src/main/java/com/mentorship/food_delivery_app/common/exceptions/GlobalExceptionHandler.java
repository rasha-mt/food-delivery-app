package com.mentorship.food_delivery_app.common.exceptions;

import com.mentorship.food_delivery_app.cart.exceptions.CartDomainException;
import com.mentorship.food_delivery_app.common.dto.ErrorResponseDto;
import com.mentorship.food_delivery_app.cart.dto.Status;
import com.mentorship.food_delivery_app.cart.exceptions.CartLockedException;
import com.mentorship.food_delivery_app.cart.exceptions.CartNotFoundException;
import com.mentorship.food_delivery_app.cart.exceptions.MenuItemNotFoundException;
import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
                ErrorMessage.VALIDATION_ERROR.getErrorMessage(),
                message,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CartDomainException.class)
    public ResponseEntity<ErrorResponseDto> handleCartDomainException(CartDomainException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getHttpStatus().value(),
                ex.getErrorCode(),
                ex.getMessage(),
                LocalDateTime.now());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        "An unexpected error occurred",
                        LocalDateTime.now()));
    }
}
