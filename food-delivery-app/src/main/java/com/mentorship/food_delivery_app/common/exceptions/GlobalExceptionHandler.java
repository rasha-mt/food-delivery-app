package com.mentorship.food_delivery_app.common.exceptions;

import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import jakarta.persistence.RollbackException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j

public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ApiErrorResponse errorResponse =
                ApiErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorCode(ErrorMessage.VALIDATION_ERROR.getErrorMessage())
                    .message(message)
                    .timestamp(LocalDateTime.now())
                    .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
//
//    @ExceptionHandler(CartDomainException.class)
//    public ResponseEntity<ErrorResponseDto> handleCartDomainException(CartDomainException ex) {
//        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getHttpStatus().value(),
//                ex.getErrorCode(),
//                ex.getMessage(),
//                LocalDateTime.now());
//        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        log.error("=== NotFound ===", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "status", 404,
                        "errorCode", "RESOURCE_NOT_FOUND",
                        "message", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex) {
        log.error("=== UNEXPECTED ERROR ===", ex); // ✅ prints full stack trace
        return ResponseEntity.status(500).body(Map.of(
                "status", 500,
                "errorCode", "Internal Server Error",
                "message", ex.getMessage(),
                "cause", ex.getClass().getName(),
                "timestamp", LocalDateTime.now()
        ));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            ApiException ex
    )
    {

        log.error("Api Exception", ex);

        return ResponseEntity.status(ex.getStatus())
                .body(
                        ApiErrorResponse.builder()
                                .status(ex.getStatus().value())
                                .errorCode(ex.getErrorCode())
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler({
            TransactionSystemException.class,
            RollbackException.class
    })
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
            Exception ex
    ) {

        Throwable root = getRootCause(ex);

        if (root instanceof ConstraintViolationException validationEx) {

            List<String> errors = validationEx.getConstraintViolations()
                    .stream()
                    .map(v ->
                            v.getPropertyPath() + ": " + v.getMessage()
                    )
                    .toList();

            return ResponseEntity.badRequest()
                    .body(
                            ApiErrorResponse.builder()
                                    .status(400)
                                    .errorCode("VALIDATION_ERROR")
                                    .message("Validation failed")
                                    .errors(errors)
                                    .timestamp(LocalDateTime.now())
                                    .build()
                    );
        }

        return ResponseEntity.internalServerError()
                .body(
                        ApiErrorResponse.builder()
                                .status(500)
                                .errorCode("INTERNAL_SERVER_ERROR")
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    private Throwable getRootCause(Throwable throwable) {

        Throwable cause = throwable;

        while (cause.getCause() != null) {
            cause = cause.getCause();
        }

        return cause;
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainException(DomainException ex) {

        log.error("Domain Exception", ex);

        return ResponseEntity
                .status(ex.getStatus())
                .body(
                        ApiErrorResponse.builder()
                                .status(ex.getStatus().value())
                                .errorCode(ex.getErrorCode())
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }
}
