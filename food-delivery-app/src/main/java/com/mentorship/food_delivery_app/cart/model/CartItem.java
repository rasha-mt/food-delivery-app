package com.mentorship.food_delivery_app.cart.model;

import com.mentorship.food_delivery_app.restaurant.model.MenuItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

import java.util.UUID;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JoinColumn(name = "cart_item_id")
    private UUID id;

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

    public BigDecimal getTotalPrice() {
        return this.menuItem.getMenuItemPrice().multiply(BigDecimal.valueOf(this.cartItemQuantity));
    }

    public  static CartItem addItem(MenuItem menuItem, Cart cart) {

        return CartItem.builder()
                .menuItem(menuItem)
                .cart(cart)
                .build();
    }

}