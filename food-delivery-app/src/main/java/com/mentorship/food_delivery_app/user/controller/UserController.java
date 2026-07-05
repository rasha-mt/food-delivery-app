package com.mentorship.food_delivery_app.user.controller;

import com.mentorship.food_delivery_app.auth.service.JwtService;
import com.mentorship.food_delivery_app.auth.dto.JwtResponse;
import com.mentorship.food_delivery_app.auth.dto.request.VerifyOtpRequest;
import com.mentorship.food_delivery_app.auth.exception.BadCredentialsException;
import com.mentorship.food_delivery_app.auth.service.AuthService;
import com.mentorship.food_delivery_app.user.dto.LoginResponse;
import com.mentorship.food_delivery_app.user.dto.request.LoginRequest;
import com.mentorship.food_delivery_app.user.enums.AuthStatus;
import com.mentorship.food_delivery_app.user.model.User;
import com.mentorship.food_delivery_app.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){

        // 1. Validate email + password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // 2. Get user
        User user = userRepository.findByUserEmail(request.email())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        authService.requestOtp(user);

        return new LoginResponse(
                AuthStatus.OTP_REQUIRED,
                "OTP sent to email"
        );
    }

    @PostMapping("/verify-otp")
    public JwtResponse verify(@RequestBody VerifyOtpRequest request) {

        JwtResponse verifyOtp=authService.verifyOtp(request);

        if (verifyOtp != null) {
            throw new BadCredentialsException("Invalid OTP");
        }

        return verifyOtp;
    }

}
