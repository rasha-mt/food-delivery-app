package com.mentorship.food_delivery_app.customer.model;

import com.mentorship.food_delivery_app.cart.model.Cart;
import com.mentorship.food_delivery_app.payment.model.PaymentIntegrationType;
import com.mentorship.food_delivery_app.payment.model.PaymentTypeConfig;
import com.mentorship.food_delivery_app.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id")
    private UUID id;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "customer_preferred_payment_id")
     private PaymentTypeConfig preferredPaymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "customer_user_id",
            referencedColumnName = "user_id",
            nullable = false,
            unique = true
    )
    private User user;
}