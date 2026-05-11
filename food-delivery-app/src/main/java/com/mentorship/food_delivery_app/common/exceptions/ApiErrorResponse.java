package com.mentorship.food_delivery_app.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorResponseDto {
    private int status;
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;
}
