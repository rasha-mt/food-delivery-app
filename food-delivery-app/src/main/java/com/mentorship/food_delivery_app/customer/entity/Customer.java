package com.mentorship.food_delivery_app.customer.entity;

import com.mentorship.food_delivery_app.cart.entity.Cart;
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
}