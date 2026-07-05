package com.mentorship.food_delivery_app.auth.service;

import com.mentorship.food_delivery_app.auth.dto.JwtResponse;
import com.mentorship.food_delivery_app.auth.dto.request.VerifyOtpRequest;
import com.mentorship.food_delivery_app.auth.repository.EmailOtpRepository;
import com.mentorship.food_delivery_app.user.model.User;
import com.mentorship.food_delivery_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmailOtpRepository otpRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    // 1. REQUEST OTP
    public void requestOtp(User user) {

        // generate OTP
        String otp = otpService.generateOtp();

        // hash OTP before storing
        String hashedOtp = passwordEncoder.encode(otp);

        otpService.saveOtp(user,otp);

        // send OTP via email
        emailService.sendOtp(user.getUserEmail(), otp);
    }

    // 2. VERIFY OTP + LOGIN
    public JwtResponse verifyOtp(VerifyOtpRequest request) {

        User user = userRepository
                .findByUserEmail(request.email())
                .orElseThrow();

        otpService.verify(user, request.otp());

        String accessToken =
                jwtService.generateAccessToken(user);

        String refreshToken =
                jwtService.generateRefreshToken(user);

        refreshTokenService.save(user, refreshToken);

        return new JwtResponse(
                accessToken,
                refreshToken,
                "Bearer",
                900
        );
    }
}
