package com.mentorship.food_delivery_app.factory;

import com.mentorship.food_delivery_app.cart.model.Cart;
import com.mentorship.food_delivery_app.customer.entity.Customer;

import java.util.ArrayList;
import java.util.UUID;

public class CartFactory {

    public static Cart create(Customer customer) {

            Cart cart = new Cart();

            cart.setCartId(
                    UUID.randomUUID()
            );

            cart.setCustomer(customer);

            cart.setCartItems(
                    new ArrayList<>()
            );

            return cart;
        }
}
