package com.mentorship.food_delivery_app.cart.repository;

import com.mentorship.food_delivery_app.cart.model.Cart;
import com.mentorship.food_delivery_app.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {


    List<CartItem> findByCartId(UUID cartId);

    void deleteByIdCartItemCartId(UUID cartId);

    Optional<CartItem> findByCartIdAndMenuItemId(Cart cart, UUID menuItemId);
   /* @Query("""
        SELECT new com.mentorship.food_delivery_app.cart.dto.CartItemView(
            m.menuItemName,
            ci.cartItemQuantity,
            m.menuItemDescription,
            m.menuItemPrice
        )
        )
        FROM CartItem ci
        JOIN ci.menuItem m
        WHERE ci.id.cartItemCartId = :cartId
    """)
    List<CartItemView> findCartItemViews(UUID cartId);*/
}