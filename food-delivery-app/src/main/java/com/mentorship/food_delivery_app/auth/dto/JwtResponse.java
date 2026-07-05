package com.mentorship.food_delivery_app.otp.dto;

public record JwtResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn
) {}
