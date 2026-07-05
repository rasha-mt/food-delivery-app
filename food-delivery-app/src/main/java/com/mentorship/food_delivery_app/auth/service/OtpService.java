package com.mentorship.food_delivery_app.auth.service;

import com.mentorship.food_delivery_app.auth.exception.BadCredentialsException;
import com.mentorship.food_delivery_app.auth.models.EmailOtp;
import com.mentorship.food_delivery_app.auth.repository.EmailOtpRepository;
import com.mentorship.food_delivery_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final SecureRandom random = new SecureRandom();
    private final EmailOtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailOtpRepository repository;

    public String generateOtp() {
        return String.format("%06d", random.nextInt(1_000_000));
    }

    public void verify(User user, String otp) {

        EmailOtp entity = otpRepository
                .findTopByEmailAndUsedFalseOrderByIdDesc(user.getUserEmail())
                .orElseThrow(() ->
                        new BadCredentialsException("OTP not found"));

        if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadCredentialsException("OTP expired");
        }

        if (!passwordEncoder.matches(otp, entity.getOtpHash())) {
            throw new BadCredentialsException("Invalid OTP");
        }

        entity.setUsed(true);

        otpRepository.save(entity);
    }

    public void saveOtp(User user, String otp) {

        // optional: invalidate previous OTPs
        invalidatePreviousOtps(user.getUserEmail());

        EmailOtp entity = new EmailOtp();

        entity.setEmail(user.getUserEmail());

        // hash OTP before saving
        entity.setOtpHash(passwordEncoder.encode(otp));

        // OTP valid for 5 minutes
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        entity.setUsed(false);
        entity.setAttempts(0);

        repository.save(entity);
    }

    // OPTIONAL: invalidate old OTPs
    private void invalidatePreviousOtps(String email) {

        List<EmailOtp> otps =
                repository.findAll()
                        .stream()
                        .filter(o -> o.getEmail().equals(email) && !o.isUsed())
                        .toList();

        otps.forEach(o -> o.setUsed(true));

        repository.saveAll(otps);
    }


}