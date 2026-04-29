package com.mentorship.food_delivery_app.factory;

import com.mentorship.food_delivery_app.cart.entity.Cart;
import com.mentorship.food_delivery_app.cart.entity.CartItem;
import com.mentorship.food_delivery_app.cart.entity.CartItemId;
import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;


import java.awt.*;
import java.util.UUID;

public class CartItemsFactory {

    public static CartItem create(
            Cart cart,
            MenuItem menuItem,
            int quantity
    ) {

        CartItem item = new CartItem();

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
