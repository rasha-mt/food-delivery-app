package com.mentorship.food_delivery_app.factory;

import com.mentorship.food_delivery_app.cart.model.Cart;
import com.mentorship.food_delivery_app.cart.model.CartItem;
import com.mentorship.food_delivery_app.cart.repository.CartItemRepository;
import com.mentorship.food_delivery_app.restaurant.model.MenuItem;
import com.mentorship.food_delivery_app.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
@Component
public class CartItemFactory {
    @Autowired
    MenuItemFactory menuItemFactory;
    @Autowired
    CartItemRepository cartItemRepository;

    public  List<CartItem> create(
            int count, Cart cart, UUID restaurantId
    ) {

            List<CartItem> cartItems=new ArrayList<>();

            for (int i = 0; i < count; i++) {
                MenuItem menuItem =  menuItemFactory.create(restaurantId);

                CartItem cartItem =CartItem.addItem(menuItem,cart);
                cartItem.setCartItemQuantity(i+1);
                cartItems.add(cartItem);
            }
            return cartItems;
    }

    public  List<CartItem> createWithMenuItem(
            Cart cart, UUID restaurantId,MenuItem menuItem
    ) {

        List<CartItem> cartItems=new ArrayList<>();

            CartItem cartItem =CartItem.addItem(menuItem,cart);
            cartItem.setCartItemQuantity(1);
            cartItems.add(cartItem);

        return cartItems;
    }

}
