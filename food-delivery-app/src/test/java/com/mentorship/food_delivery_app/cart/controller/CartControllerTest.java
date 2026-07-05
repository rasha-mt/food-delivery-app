package com.mentorship.food_delivery_app.cart.controller;

import com.mentorship.food_delivery_app.BaseIntegrationTest;
import com.mentorship.food_delivery_app.cart.model.Cart;
import com.mentorship.food_delivery_app.cart.model.CartItem;
import com.mentorship.food_delivery_app.cart.repository.CartItemRepository;
import com.mentorship.food_delivery_app.cart.repository.CartRepository;
import com.mentorship.food_delivery_app.auth.service.JwtService;
import com.mentorship.food_delivery_app.customer.model.Customer;
import com.mentorship.food_delivery_app.customer.repository.CustomerRepository;
import com.mentorship.food_delivery_app.factory.CartFactory;
import com.mentorship.food_delivery_app.factory.CartItemFactory;
import com.mentorship.food_delivery_app.factory.CustomerFactory;
import com.mentorship.food_delivery_app.factory.MenuItemFactory;
import com.mentorship.food_delivery_app.auth.dto.JwtResponse;
import com.mentorship.food_delivery_app.restaurant.model.MenuItem;
import com.mentorship.food_delivery_app.restaurant.repository.MenuItemRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class CartControllerTest extends BaseIntegrationTest {


    private static final Logger log = LoggerFactory.getLogger(CartControllerTest.class);
    private Customer customer;
    private UUID restaurantId;
    private String token;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CustomerFactory customerFactory;
    @Autowired
    private CartFactory cartFactory;
    @Autowired
    private CartItemFactory cartItemFactory;
    @Autowired
    private MenuItemFactory menuItemFactory;
    @Autowired
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;

        // ✅ delete in correct FK order
        cartItemRepository.deleteAll();
        cartRepository.deleteAll();
        menuItemRepository.deleteAll();
        customerRepository.deleteAll();

        customer = customerFactory.create();
        JwtResponse token = jwtService.generateAccessToken(customer.getUser().getUserEmail());

        restaurantId = UUID.randomUUID();
    }

    // ==================== POST /api/v1/cart ====================

    @Test
    void shouldCreateCartAndReturnCartDto() {
        Cart cart = cartFactory.create(customer);
        cartRepository.save(cart);
        List<CartItem> cartItems = cartFactory.cartWithNItems(cart, 2);
        cartItemRepository.saveAll(cartItems);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/cart")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.cartId", notNullValue())
                .body("data.items", hasSize(2))
                .body("data.totalPrice", nullValue());
    }

    @Test
    void shouldAddItemToCart() {
        MenuItem menuItem = menuItemFactory.create(restaurantId);

        menuItemRepository.save(menuItem);

        String request = """
                {
                    "menuItemId": "%s",
                    "quantity": 2
                }
                """.formatted(menuItem.getId());

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/cart/items")
                .then()
                .log().all()
                .statusCode(200)
                .body(equalTo("successfully added item"));
    }

//        @Test
//        void shouldReturnSameCartOnSecondCreate() {
//            // first call
//            String cartId =
//                    given()
//                            .contentType(ContentType.JSON)
//                            .when()
//                            .post("/api/v1/cart")
//                            .then()
//                            .statusCode(200)
//                            .extract().path("cartId");
//
//            // second call — same cart
//            given()
//                    .contentType(ContentType.JSON)
//                    .when()
//                    .post("/api/v1/cart")
//                    .then()
//                    .log().body()
//                    .statusCode(200)
//                    .body("cartId", equalTo(cartId)); // ✅ same cart ID
//        }
//
    // ==================== GET /api/v1/cart ====================

    @Test
    void shouldReturn404WhenNoCart() {
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/cart")
                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    void shouldReturn400WhenAddingItemToLockedCart() {
        cartFactory.createLocked(customer);
        MenuItem menuItem = menuItemFactory.create(restaurantId);

        String body = """
                    {
                        "menuItemId": "%s",
                        "quantity": 1
                    }
                """.formatted(menuItem.getId());

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/cart/items")
                .then()
                .log().body()
                .statusCode(409)
                .body("status", equalTo(409))
                .body("errorCode", equalTo("CART_IS_LOCKED"));
    }

    //
    @Test
    void shouldReturn400WhenAddingItemFromDifferentRestaurant() {

        Cart cart = cartFactory.createWithRestaurant(customer, restaurantId);
        MenuItem firstItem = menuItemFactory.createWith("Burger", BigDecimal.valueOf(10.00), restaurantId);
        cartItemFactory.createWithMenuItem(cart, restaurantId, firstItem);

        // item from different restaurant
        UUID secondRestaurantID=UUID.randomUUID();
        MenuItem otherItem = menuItemFactory.createWith("Sushi", BigDecimal.valueOf(20.00), secondRestaurantID);

        String body = """
                    {
                        "menuItemId": "%s",
                        "quantity": 1
                    }
                """.formatted(otherItem.getId());

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/cart/items")
                .then()
                .log().body()
                .statusCode(409)
                .body("errorCode", equalTo("DIFFERENT_RESTRAUNT_ERROR"));
    }
//
        @Test
        void shouldReturn400WhenQuantityIsZero() {
            MenuItem menuItem = menuItemFactory.create(restaurantId);

            String body = """
            {
                "menuItemId": "%s",
                "quantity": 0
            }
        """.formatted(menuItem.getId());

            given()
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post("/api/v1/cart/items")
                    .then()
                    .log().body()
                    .statusCode(400)
                    .body("errorCode", equalTo("Validation error."));;
        }
//
//        // ==================== DELETE /api/v1/cart/clear/{cartId} ====================
//
        @Test
        void shouldClearCart() {
            Cart cart = cartFactory.createWithRestaurant(customer, restaurantId);
            MenuItem menuItem = menuItemFactory.create(restaurantId);
            cartItemFactory.createWithMenuItem(cart,restaurantId, menuItem);

            given()
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                    .when()
                    .delete("/api/v1/cart/{cartId}", cart.getId())
                    .then()
                    .log().body()
                    .statusCode(204);

            // verify cart is empty
            given()
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/api/v1/cart")
                    .then()
                    .body("data.items", hasSize(0));
        }
//
//        @Test
//        void shouldReturn400WhenClearingLockedCart() {
//            Cart cart = cartFactory.createLocked(customer);
//
//            given()
//                    .contentType(ContentType.JSON)
//                    .when()
//                    .delete("/api/v1/cart/clear/{cartId}", cart.getId())
//                    .then()
//                    .log().body()
//                    .statusCode(400);
//        }
//
        @Test
        void shouldReturn404WhenClearingWrongCartId() {
            cartFactory.create(customer);

            given()
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                    .when()
                    .delete("/api/v1/cart/{cartId}", UUID.randomUUID()) // wrong ID
                    .then()
                    .log().body()
                    .statusCode(404);
        }
}