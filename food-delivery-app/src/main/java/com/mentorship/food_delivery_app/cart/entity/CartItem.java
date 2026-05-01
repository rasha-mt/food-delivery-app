package com.mentorship.food_delivery_app.cart.entity;

import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Min(1)
    @Column(name = "cart_item_quantity", nullable = false)
    private int cartItemQuantity;

    @Column(name = "cart_item_note")
    private String cartItemNote;
}