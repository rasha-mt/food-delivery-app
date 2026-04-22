package com.mentorship.ecommerce_app.cart.service;

import com.mentorship.ecommerce_app.cart.dto.*;
import com.mentorship.ecommerce_app.cart.entity.*;
import com.mentorship.ecommerce_app.cart.exceptions.CartLockedException;
import com.mentorship.ecommerce_app.cart.exceptions.CartNotFoundException;
import com.mentorship.ecommerce_app.cart.exceptions.MenuItemNotFoundException;
import com.mentorship.ecommerce_app.cart.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;

    //  CREATE CART
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
    public CartResponse viewCart(UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for customer"));

        return getCartItemsView(cart);
    }

    //  ADD ITEM
    @Transactional
    public AddCartResponse addItem(UUID itemId, int quantity, UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        if (cart.isLocked()) {
            throw new CartLockedException("Item cannot be added. Cart already checked out.");
        }

        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found"));

        CartItemId id = new CartItemId(cart.getCartId(), itemId);

        CartItem existingItem = cartItemRepository.findById(id).orElse(null);

        if (existingItem != null) {
            existingItem.setCartItemQuantity(existingItem.getCartItemQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem(id, menuItem, quantity, null);
            cartItemRepository.save(newItem);
        }

        return new AddCartResponse(new Status("200", "Item added successfully"));
    }

    // CLEAR CART
    @Transactional
    public ClearCartResponse clearCart(UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        if (cart.isLocked()) {
            throw new CartLockedException("Item cannot be added. Cart already checked out.");
        }
        cartItemRepository.deleteByIdCartItemCartId(cart.getCartId());

        return new ClearCartResponse(
                new Status("200", "Cart cleared successfully")
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