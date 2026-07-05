package com.mentorship.food_delivery_app.auth.repository;

import com.mentorship.food_delivery_app.auth.models.EmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailOtpRepository extends JpaRepository<EmailOtp, Long> {

    Optional<EmailOtp> findTopByEmailOrderByIdDesc(String email);
    Optional<EmailOtp> findTopByEmailAndUsedFalseOrderByIdDesc(String email);

}