package com.mentorship.food_delivery_app.cart.model;

import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.restaurant.model.MenuItem;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cart", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cart_customer_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_customer_id", nullable = false,updatable = false)
    private Customer customer;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    @Column(name = "cart_current_rest_id")
    private UUID cartCurrentRestId;

    @Column(name = "is_locked")
    private boolean isLocked;

//    public boolean checkIsLocked() {
//        return this.isLocked;
//    }

    public BigDecimal cartTotal;


    public static Cart buildCart(Customer customer) {
        return Cart.builder().customer(customer).isLocked(false).build();
    }

}