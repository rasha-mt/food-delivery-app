package com.mentorship.food_delivery_app.cart.entity;

import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @EmbeddedId
    private CartItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("menuItemId") // maps to field inside EmbeddedId
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Min(1)
    @Column(name = "cart_item_quantity", nullable = false)
    private int cartItemQuantity;

    @Column(name = "cart_item_note")
    private String cartItemNote;

    @ManyToOne
    private Cart cart;

    public BigDecimal getTotalPrice() {
        return this.menuItem.getMenuItemPrice().multiply(BigDecimal.valueOf(this.cartItemQuantity));
    }
}