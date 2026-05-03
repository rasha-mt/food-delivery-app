package com.mentorship.food_delivery_app.factory;

import com.mentorship.food_delivery_app.cart.model.Cart;
import com.mentorship.food_delivery_app.cart.model.CartItem;
import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;

import java.util.concurrent.ThreadLocalRandom;

public class CartItemFactory {

    public static CartItem create(
            Cart cart,
            MenuItem menuItem
    ) {

        int quantity =
                ThreadLocalRandom.current()
                        .nextInt(1,6);
        CartItem item = CartItem
                .builder()
                .cartItemQuantity(quantity)
                .cart(cart)
                .menuItem(menuItem)
                .build();

        return item;
    }
}
