package com.mentorship.food_delivery_app.common.secuirty.filter;

import com.mentorship.food_delivery_app.common.exceptions.UnauthorizedException;
import com.mentorship.food_delivery_app.common.secuirty.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        // ✅ 1. Validate FIRST
        if (token == null  || !jwtService.validate(token)) {
            throw new UnauthorizedException("Invalid or expired token");
        }

        // ✅ 2. Extract AFTER validation
        UUID customerId = jwtService.extractCustomerId(token);

        // (optional but recommended)
        if (customerId == null) {
            throw new UnauthorizedException("Invalid token payload");
        }

        // ✅ 3. Set authentication
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        customerId,
                        null,
                        List.of()
                );

        SecurityContextHolder.getContext().setAuthentication(auth);

        // ✅ 4. Continue chain
        filterChain.doFilter(request, response);
    }
}
