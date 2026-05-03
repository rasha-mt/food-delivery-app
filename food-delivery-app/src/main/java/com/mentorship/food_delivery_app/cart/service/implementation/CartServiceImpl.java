package com.mentorship.food_delivery_app.cart.service.implementation;

import com.mentorship.food_delivery_app.cart.dto.*;
import com.mentorship.food_delivery_app.cart.dto.CartDto;
import com.mentorship.food_delivery_app.cart.mapper.CartMapper;
import com.mentorship.food_delivery_app.cart.model.*;
import com.mentorship.food_delivery_app.cart.exceptions.*;
import com.mentorship.food_delivery_app.cart.repository.*;
import com.mentorship.food_delivery_app.cart.service.contract.CartService;
import com.mentorship.food_delivery_app.common.dto.StatusDto;
import com.mentorship.food_delivery_app.common.enums.SuccessMessage;
import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.customer.service.CustomerService;
import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import com.mentorship.food_delivery_app.restaurant.exceptions.MenuItemDiffRest;
import com.mentorship.food_delivery_app.restaurant.exceptions.MenuItemNotFoundException;
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
    private final MenuItemRepository menuItemRepository;
    private final CustomerService customerService;

    //  CREATE CART
   @Transactional
    @Override
    public CartDto createCart(UUID customerId) {

       Cart cart = cartRepository.findByCustomerId(customerId).orElse(null);
       Customer customer = customerService.getCustomer(customerId);
       
       if (cart == null) {
           cart = new Cart();
           cart.setCustomer(customer);
           cart.setIsLocked('N');
           cart = cartRepository.save(cart);
       }

       return toCartDto(cart);
   }

    //  VIEW CART
    @Transactional(readOnly = true)
    @Override
    public CartDto viewCart(UUID customerId) {

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(CartNotFoundException::new);

        return toCartDto(cart);
    }

    //  ADD ITEM
    @Transactional
    @Override
    public void addItem(UUID menuItemId, int quantity, UUID customerId) {

        Cart cart = getCartByCustomerId(customerId);

        if (cart.isLocked()) {
            throw new CartLockedException();
        }

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(MenuItemNotFoundException::new);

        if (cart.getCartCurrentRestId() != null &&
                !cart.getCartCurrentRestId().equals(menuItem.getRestaurantMenuId())) {
            throw new MenuItemDiffRest();
        }
// Set the restaurant on first item add
        if (cart.getCartCurrentRestId() == null) {
            cart.setCartCurrentRestId(menuItem.getRestaurantMenuId());
            cartRepository.save(cart);
        }

        CartItem item = cartItemRepository
                .findByCartIdAndMenuItemId(cart, menuItemId)
                .orElseGet(() -> {
                   CartItem newItem=  CartItem
                           .builder()
                           .menuItem(menuItem)
                           .cart(cart)
                           .cartItemQuantity(quantity)
                           .build();
                   return newItem;
                });

        item.setCartItemQuantity(quantity);

        cartItemRepository.save(item);
    }

    // CLEAR CART
    @Transactional
    @Override
    public void clearCart(UUID  cartId, UUID customerId) {
        Cart cart = getCartByCustomerId(customerId);

        if(!cart.getCartId().equals(cartId)) {
            throw new CartNotFoundException();
        }

        if (cart.isLocked()) {
            throw new CartLockedException();
        }
        cartItemRepository.deleteByIdCartItemCartId(cart.getCartId());
    }

    //  VIEW MAPPING
    private List<CartItemDto> CartItemsMapping(Cart cart) {

        return cartItemRepository
                .findByCartId(cart.getCartId())
                .stream()
                .map(CartMapper::toDto)
                .toList();

    }

    private CartDto toCartDto(Cart cart ){

       List<CartItemDto> cartItems = CartItemsMapping(cart);

        return new CartDto(
                cart.getCartId(),
                cartItems,
                cart.getCartTotal()
        );
    }

    private Cart getCartByCustomerId(UUID customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(CartNotFoundException::new);
    }
}