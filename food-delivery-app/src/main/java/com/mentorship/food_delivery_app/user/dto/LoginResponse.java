package com.mentorship.food_delivery_app.user.dto;

import com.mentorship.food_delivery_app.user.enums.AuthStatus;

public record LoginResponse(

        AuthStatus status,

        String message

) {
}