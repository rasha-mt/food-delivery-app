package com.mentorship.ecommerce_app.cart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @EmbeddedId
    private CartItemId id;

   @ManyToOne
    @MapsId("menuItemId") // maps to field inside EmbeddedId
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(name = "cart_item_quantity", nullable = false)
    private int cartItemQuantity;

    @Column(name = "cart_item_note")
    private String cartItemNote;
}