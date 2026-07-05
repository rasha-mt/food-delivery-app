package com.mentorship.food_delivery_app.otp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailOtp {

    @Id
    @GeneratedValue
    private UUID id;

    private String email;

    private String otpHash;

    private LocalDateTime expiresAt;

    private Integer attempts;

    private boolean used;
}
