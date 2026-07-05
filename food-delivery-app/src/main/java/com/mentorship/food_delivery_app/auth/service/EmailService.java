package com.mentorship.food_delivery_app.auth.service;

import com.mentorship.food_delivery_app.auth.enums.EmailStatus;
import com.mentorship.food_delivery_app.auth.models.EmailLog;
import com.mentorship.food_delivery_app.auth.repository.EmailLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;

    @Async("smsExecutor")
    public void sendOtp(String email, String otp) {

        EmailLog log = new EmailLog();

        log.setRecipient(email);
        log.setSubject("Your OTP Code");

        log.setSentAt(LocalDateTime.now());

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp);

            mailSender.send(message);

            log.setStatus(EmailStatus.SENT);

        } catch (Exception ex) {

            log.setStatus(EmailStatus.FAILED);
            log.setErrorMessage(ex.getMessage());

            throw ex;

        } finally {

            emailLogRepository.save(log);

        }
    }
}