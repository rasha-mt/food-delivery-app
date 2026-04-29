package com.mentorship.food_delivery_app.cart.controller;

import com.mentorship.food_delivery_app.cart.dto.CartItemView;
import com.mentorship.food_delivery_app.cart.dto.CartResponse;
import com.mentorship.food_delivery_app.cart.entity.Cart;
import com.mentorship.food_delivery_app.cart.service.contract.CartService;
import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.factory.CartFactory;
import com.mentorship.food_delivery_app.factory.CustomerFactory;
import com.mentorship.food_delivery_app.factory.MenuItemFactory;
import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void shouldReturnCartContent() throws Exception {

        Customer customer =
                CustomerFactory.create();

        Cart cart =
                CartFactory.create(customer);

        MenuItem menuItem =
                MenuItemFactory.create();

        UUID cartId = cart.getCartId();

        CartItemView item =
                new CartItemView(
                        menuItem.getMenuItemId(),
                        menuItem.getMenuItemName(),
                        2,
                        new BigDecimal("20"),
                        " "
                );

        CartResponse response =
                new CartResponse(
                        cartId,
                        List.of(item),
                        new BigDecimal("40")
                );

        when(
                cartService.viewCart(cartId)
        ).thenReturn(response);


        mockMvc.perform(
                        get("/api/cart/{id}", cartId)
                )
                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$.cartId")
                                .value(cartId.toString())
                )

                .andExpect(
                        jsonPath("$.items[0].name")
                                .value(
                                        menuItem.getMenuItemName()
                                )
                );
    }
}