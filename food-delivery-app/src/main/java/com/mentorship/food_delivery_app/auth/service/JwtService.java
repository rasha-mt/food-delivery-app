package com.mentorship.food_delivery_app.common.secuirty.jwt;

import com.mentorship.food_delivery_app.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final String SECRET = "secret-key";
    @Value("${JWT_SECRET}")
    private String jwtSecret;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    public String generateAccessToken(User user) {

        return Jwts.builder()
                .setSubject(user.getUserEmail())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 900000)
                )
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public UUID  extractCustomerId(String token) {
        String subject = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return UUID.fromString(subject);
    }

    // ✅ VALIDATION METHOD
    public boolean validate(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().after(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    public String generateRefreshToken(User user) {

        return Jwts.builder()
                .setSubject(user.getUserEmail())
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 604800000)
                )
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
