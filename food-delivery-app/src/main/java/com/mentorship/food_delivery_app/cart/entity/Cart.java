package com.mentorship.food_delivery_app.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "cart", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cart_customer_id")
})
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartId;

    @Column(name = "cart_customer_id", nullable = false)
    private UUID cartCustomerId;

    @Column(name = "cart_current_rest_id")
    private UUID cartCurrentRestId;

    @Column(name = "is_locked")
    private Character isLocked;

    public boolean isLocked() {
        return this.isLocked == 'Y';
    }
}