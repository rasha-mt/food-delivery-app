package com.mentorship.food_delivery_app.payment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PaymentIntegrationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_integration_type_id")
    private long id ;

    @Column(name = "payment_integration_type_name")
    private String payment_name;
}
