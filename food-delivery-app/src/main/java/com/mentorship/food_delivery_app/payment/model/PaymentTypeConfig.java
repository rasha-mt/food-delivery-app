package com.mentorship.food_delivery_app.payment.model;

import com.mentorship.food_delivery_app.order.model.Order;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PaymentTypeConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_type_config_id")
    private int id ;

    @ManyToOne
    @JoinColumn(name = "payment_integration_type_id")
    private PaymentIntegrationType paymentType ;
}
