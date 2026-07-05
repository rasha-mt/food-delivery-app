package com.mentorship.food_delivery_app.auth.models;

import com.mentorship.food_delivery_app.auth.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_logs")
@Getter
@Setter
@NoArgsConstructor
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;

    private String subject;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    @Column(length = 2000)
    private String errorMessage;

    private LocalDateTime sentAt;
}