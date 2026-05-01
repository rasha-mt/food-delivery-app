package com.mentorship.food_delivery_app.cart.repository;

import com.mentorship.food_delivery_app.cart.dto.CartItemView;
import com.mentorship.food_delivery_app.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {


    List<CartItem> findByIdCartItemCartId(UUID cartId);

    void deleteByIdCartItemCartId(UUID cartId);


    @Query("""
        SELECT new com.mentorship.food_delivery_app.cart.dto.CartItemView(
            m.menuItemName,
            ci.cartItemQuantity,
            m.menuItemDescription,
            m.menuItemPrice
        )
        FROM CartItem ci
        JOIN ci.menuItem m
        WHERE ci.id.cartItemCartId = :cartId
    """)
    List<CartItemView> findCartItemViews(UUID cartId);
}