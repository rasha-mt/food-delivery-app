package com.mentorship.food_delivery_app.auth.dto;

public record JwtResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn
) {}
