package com.mentorship.food_delivery_app.customer.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "customer_address")
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private UUID id;

    private  String label;
    private String city;
    @Column(name = "street")
    private String street;

    private String building;

    private String apartment;

    private String phoneNumber;
    String note;

    @ManyToOne
    @JoinColumn(name = "customer_address_customer_id", nullable = false,updatable = false)
    private Customer customer;

}
