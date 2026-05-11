package com.mentorship.food_delivery_app.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter

public class ApiErrorResponse {
    private int status;
    private String errorCode;
    private String message;
    private List<String> errors;
    private LocalDateTime timestamp;
}
