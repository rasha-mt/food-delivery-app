package com.mentorship.food_delivery_app.cart.service.implementation;

import com.mentorship.food_delivery_app.cart.dto.*;
import com.mentorship.food_delivery_app.cart.entity.*;
import com.mentorship.food_delivery_app.cart.exceptions.CartLockedException;
import com.mentorship.food_delivery_app.cart.exceptions.CartNotFoundException;
import com.mentorship.food_delivery_app.cart.exceptions.MenuItemNotFoundException;
import com.mentorship.food_delivery_app.cart.repository.*;
import com.mentorship.food_delivery_app.cart.service.contract.CartService;
import com.mentorship.food_delivery_app.common.enums.ErrorMessage;
import com.mentorship.food_delivery_app.common.enums.SuccessMessage;
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

    //  CREATE CART
   @Transactional
    @Override
    public CartResponse createCart(UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCartCustomerId(customerId);
                    newCart.setIsLocked('N');
                    return cartRepository.save(newCart);
                });

        return getCartItemsView(cart);
    }

    //  VIEW CART
    @Override
    public CartResponse viewCart(UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId)
                .orElseThrow(() -> new CartNotFoundException(ErrorMessage.CART_NOT_FOUND.getErrorMessage()));

        return getCartItemsView(cart);
    }

    //  ADD ITEM
    @Transactional
    @Override
    public AddCartResponse addItem(UUID itemId, int quantity, UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId)
                .orElseThrow(() -> new CartNotFoundException(ErrorMessage.CART_NOT_FOUND.getErrorMessage()));

        if (cart.isLocked()) {
            throw new CartLockedException(ErrorMessage.CART_IS_LOCKED.getErrorMessage());
        }

        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new MenuItemNotFoundException(ErrorMessage.MENU_ITEM_NOT_FOUND.getErrorMessage()));

        CartItemId id = new CartItemId(cart.getCartId(), itemId);

        CartItem existingItem = cartItemRepository.findById(id).orElse(null);

        if (existingItem != null) {
            existingItem.setCartItemQuantity(existingItem.getCartItemQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem(id, menuItem, quantity, null);
            cartItemRepository.save(newItem);
        }

        return new AddCartResponse(new Status("200", SuccessMessage.ITEM_ADDED.getSuccessMessage()));
    }

    // CLEAR CART
    @Transactional
    @Override
    public ClearCartResponse clearCart(UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId)
                .orElseThrow(() -> new CartNotFoundException(ErrorMessage.CART_NOT_FOUND.getErrorMessage()));

        if (cart.isLocked()) {
            throw new CartLockedException(ErrorMessage.CART_IS_LOCKED.getErrorMessage());
        }
        cartItemRepository.deleteByIdCartItemCartId(cart.getCartId());

        return new ClearCartResponse(
                new Status("200", SuccessMessage.CART_CLEARED.getSuccessMessage())
        );
    }

    //  VIEW MAPPING
    private CartResponse getCartItemsView(Cart cart) {

        List<CartItemView> items =
                cartItemRepository.findCartItemViews(cart.getCartId());

        double totalPrice = items.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();

        return new CartResponse(
                cart.getCartId(),
                items,
                totalPrice
        );
    }
}