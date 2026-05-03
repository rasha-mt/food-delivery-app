package com.mentorship.food_delivery_app.cart.model;

import com.mentorship.food_delivery_app.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_customer_id", nullable = false,updatable = false)
    private Customer customer;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    @Column(name = "cart_current_rest_id")
    private UUID cartCurrentRestId;

    @Column(name = "is_locked")
    private Character isLocked;

    public boolean isLocked() {
        return this.isLocked == 1;
    }

    public BigDecimal cartTotal;


}