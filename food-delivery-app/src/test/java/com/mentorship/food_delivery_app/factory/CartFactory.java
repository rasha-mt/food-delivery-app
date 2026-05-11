package com.mentorship.food_delivery_app.factory;

import com.mentorship.food_delivery_app.cart.model.Cart;
import com.mentorship.food_delivery_app.cart.model.CartItem;
import com.mentorship.food_delivery_app.cart.repository.CartRepository;
import com.mentorship.food_delivery_app.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component

public class CartFactory {
    @Autowired
    private CartRepository cartRepository;
    @Autowired private CartItemFactory cartItemFactory;
    public Cart create(Customer customer) {

        return Cart.builder()
                .customer(customer)
                .cartItems(new ArrayList<>())
                .build();
        }

    public  List<CartItem> cartWithNItems(Cart cart,int itemsNumber) {
            List<CartItem> cartItems = cartItemFactory.create(itemsNumber,cart,UUID.randomUUID());
            cart.setCartItems(cartItems);
            return cartItems;
        }

    public  Cart createLocked(Customer customer) {
        Cart cart = Cart.buildCart(customer);
        cart.setLocked(true);
        return cartRepository.save(cart);
    }

    public Cart createWithRestaurant(Customer customer, UUID restaurantId) {
        Cart cart = Cart.buildCart(customer);
        cart.setCartCurrentRestId(restaurantId);
        return cartRepository.save(cart);
    }


}
