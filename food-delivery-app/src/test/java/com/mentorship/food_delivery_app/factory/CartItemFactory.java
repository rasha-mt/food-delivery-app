package com.mentorship.food_delivery_app.factory;

import com.mentorship.food_delivery_app.cart.entity.Cart;
import com.mentorship.food_delivery_app.cart.entity.CartItem;
import com.mentorship.food_delivery_app.cart.entity.CartItemId;
import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;

import java.util.concurrent.ThreadLocalRandom;

public class CartItemFactory {

    public static CartItem create(
            Cart cart,
            MenuItem menuItem
    ) {

        CartItem item = new CartItem();
        int quantity =
                ThreadLocalRandom.current()
                        .nextInt(1,6);
        item.setId(
                new CartItemId(
                        cart.getCartId(),
                        menuItem.getMenuItemId()
                )
        );

        item.setCart(cart);

        item.setMenuItem(menuItem);

        item.setCartItemQuantity(quantity);

        return item;
    }
}
