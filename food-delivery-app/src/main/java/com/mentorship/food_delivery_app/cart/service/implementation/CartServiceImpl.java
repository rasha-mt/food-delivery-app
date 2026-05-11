package com.mentorship.food_delivery_app.cart.service.implementation;

import com.mentorship.food_delivery_app.cart.dto.*;
import com.mentorship.food_delivery_app.cart.dto.CartDto;
import com.mentorship.food_delivery_app.cart.mapper.CartMapper;
import com.mentorship.food_delivery_app.cart.model.*;
import com.mentorship.food_delivery_app.cart.exceptions.*;
import com.mentorship.food_delivery_app.cart.repository.*;
import com.mentorship.food_delivery_app.cart.service.contract.CartService;
import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.customer.service.CustomerService;
import com.mentorship.food_delivery_app.restaurant.model.MenuItem;
import com.mentorship.food_delivery_app.restaurant.exceptions.MenuItemDiffRest;
import com.mentorship.food_delivery_app.restaurant.exceptions.MenuItemNotFoundException;
import com.mentorship.food_delivery_app.restaurant.service.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuItemService menuItemService;
    private final CustomerService customerService;
    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    //  CREATE CART
   @Transactional
    @Override
    public CartDto createCart() {
       Customer customer = customerService.getCustomer();
       Cart cart=getOrCreateCart(customer);

       return toCartDto(cart);
   }

    //  VIEW CART
    @Transactional(readOnly = true)
    @Override
    public CartDto viewCart() {
        Customer customer = customerService.getCustomer();
        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(CartNotFoundException::new);

        return toCartDto(cart);
    }

    //  ADD ITEM
    @Transactional
    @Override
    public void addItem(UUID menuItemId, int quantity) {
        Customer customer = customerService.getCustomer();

        Cart cart = getOrCreateCart(customer);

        if (cart.isLocked()) {
            throw new CartLockedException();
        }

        MenuItem menuItem = menuItemService.getMenuItem(menuItemId);

        if (cart.getCartCurrentRestId() != null &&
                !cart.getCartCurrentRestId().equals(menuItem.getRestaurantMenuId())) {
            throw new MenuItemDiffRest();
        }
// Set the restaurant on first item add
        if (cart.getCartCurrentRestId() == null) {
            cart.setCartCurrentRestId(menuItem.getRestaurantMenuId());
            cartRepository.save(cart);
        }

        CartItem cartItem = cartItemRepository
                .findByCartIdAndMenuItemId(cart.getId(), menuItemId)
                .orElseGet(() -> {
                    return CartItem.addItem(menuItem,cart);
                });

        cartItem.setCartItemQuantity(quantity);

        cartItemRepository.save(cartItem);
    }

    // CLEAR CART
    @Transactional
    @Override
    public void clearCart(UUID  cartId) {
        Customer customer = customerService.getCustomer();
        Cart cart = getOrCreateCart(customer);

        if(!cart.getId().equals(cartId)) {
            throw new CartNotFoundException();
        }

        if (cart.isLocked()) {
            throw new CartLockedException();
        }
        cartItemRepository.deleteByCartId(cart.getId());
    }

    //  VIEW MAPPING
    private List<CartItemDto> CartItemsMapping(Cart cart) {

        return cartItemRepository
                .findByCartId(cart.getId())
                .stream()
                .map(CartMapper::toDto)
                .toList();

    }

    private CartDto toCartDto(Cart cart ){

       List<CartItemDto> cartItems = CartItemsMapping(cart);

        return new CartDto(
                cart.getId(),
                cartItems,
                cart.getCartTotal()
        );
    }


    private Cart getOrCreateCart(Customer customer) {
        Cart cart = cartRepository.findByCustomerId(customer.getId()).orElseGet(() -> {
            Cart newCart=Cart.buildCart(customer);
            return cartRepository.save(newCart);
        });
        return cart;
    }

}