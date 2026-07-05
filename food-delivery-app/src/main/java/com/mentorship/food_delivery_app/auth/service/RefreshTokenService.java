package com.mentorship.food_delivery_app.common.secuirty.jwt;

import com.mentorship.food_delivery_app.otp.models.RefreshToken;
import com.mentorship.food_delivery_app.otp.repository.RefreshTokenRepository;
import com.mentorship.food_delivery_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public void save(User user, String token) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setRevoked(false);
        refreshToken.setExpiresAt(
                Instant.now().plus(7, ChronoUnit.DAYS)
        );

        repository.save(refreshToken);
    }
}
