package com.mentorship.food_delivery_app.cart.service.implementation;

import com.mentorship.food_delivery_app.cart.dto.*;
import com.mentorship.food_delivery_app.cart.entity.*;
import com.mentorship.food_delivery_app.cart.exceptions.*;
import com.mentorship.food_delivery_app.cart.repository.*;
import com.mentorship.food_delivery_app.cart.service.contract.CartService;
import com.mentorship.food_delivery_app.common.dto.StatusDto;
import com.mentorship.food_delivery_app.common.enums.SuccessMessage;
import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import com.mentorship.food_delivery_app.restaurant.exceptions.MenuItemDiffRest;
import com.mentorship.food_delivery_app.restaurant.exceptions.MenuItemNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
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
    public CartResponseWrapper createCart(UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId).orElse(null);
       if (cart == null) {
           cart = new Cart();
           cart.setCartCustomerId(customerId);
           cart.setIsLocked('N');
           cart = cartRepository.save(cart);

           return new CartResponseWrapper(getCartItemsView(cart), true);
       }

       return new CartResponseWrapper(getCartItemsView(cart), false);
   }

    //  VIEW CART
    @Transactional(readOnly = true)
    @Override
    public CartResponse viewCart(UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId)
                .orElseThrow(CartNotFoundException::new);

        return getCartItemsView(cart);
    }

    //  ADD ITEM
    @Transactional
    @Override
    public AddCartResponse addItem(UUID itemId, int quantity, UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId)
                .orElseThrow(CartNotFoundException::new);

        if (cart.isLocked()) {
            throw new CartLockedException();
        }



        MenuItem menuItem = menuItemRepository.findById(itemId)
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

        CartItemId id = new CartItemId(cart.getCartId(), itemId);

        CartItem existingItem = cartItemRepository.findById(id).orElse(null);

        if (existingItem != null) {
            existingItem.setCartItemQuantity(existingItem.getCartItemQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem(id, menuItem, quantity, null);
            cartItemRepository.save(newItem);
        }

        return new AddCartResponse(new StatusDto("200", SuccessMessage.ITEM_ADDED.getSuccessMessage()));
    }

    // CLEAR CART
    @Transactional
    @Override
    public ClearCartResponse clearCart(UUID customerId) {

        Cart cart = cartRepository.findByCartCustomerId(customerId)
                .orElseThrow(CartNotFoundException::new);

        if (cart.isLocked()) {
            throw new CartLockedException();
        }
        cartItemRepository.deleteByIdCartItemCartId(cart.getCartId());

        return new ClearCartResponse(
                new StatusDto("200", SuccessMessage.CART_CLEARED.getSuccessMessage())
        );
    }

    //  VIEW MAPPING
    private CartResponse getCartItemsView(Cart cart) {

        List<CartItemView> items =
                cartItemRepository.findCartItemViews(cart.getCartId());

        BigDecimal totalPrice = items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(
                cart.getCartId(),
                items,
                totalPrice
        );
    }
}