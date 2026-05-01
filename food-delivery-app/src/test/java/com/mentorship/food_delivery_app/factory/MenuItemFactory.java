package com.mentorship.food_delivery_app.factory;

import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MenuItemFactory {
    private final static String[] names = {
            "Pizza",
            "Burger",
            "Pasta",
            "Shawarma"
    };
    private static int i = 0;

    public static MenuItem create() {

        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId(UUID.randomUUID());
        menuItem.setMenuItemName("Pizza");
        menuItem.setMenuItemName(names[i++ % names.length]);
        menuItem.setMenuItemPrice(
                BigDecimal.valueOf(
                        ThreadLocalRandom.current()
                                .nextDouble(10,100)
                ).setScale(2, RoundingMode.HALF_UP)
        );
        return menuItem;
    }
}
