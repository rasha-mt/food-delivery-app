package com.mentorship.food_delivery_app.factory;

import com.mentorship.food_delivery_app.restaurant.model.MenuItem;
import com.mentorship.food_delivery_app.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MenuItemFactory {
    private final static String[] names = {
            "Pizza",
            "Burger",
            "Pasta",
            "Shawarma"
    };
    private static int i = 0;

    private final MenuItemRepository menuItemRepository;

    public MenuItemFactory(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public  MenuItem create(UUID restaurantId) {

        MenuItem item=  MenuItem
                .builder()
                .menuItemName("Pizza")
                .menuItemPrice(  BigDecimal.valueOf(
                                ThreadLocalRandom.current()
                                        .nextDouble(10,100)))
                .createdBy(UUID.randomUUID())
                .restaurantMenuId(restaurantId)
                .build();
        return menuItemRepository.save(item);
    }

    public  MenuItem createWith(String menuItemName, BigDecimal menuItemPrice, UUID restaurantId) {
        MenuItem item=  MenuItem
                 .builder()
                 .menuItemName(menuItemName)
                 .menuItemPrice(menuItemPrice)
                 .restaurantMenuId(restaurantId)
                .createdBy(UUID.randomUUID())
                 .build();
        return menuItemRepository.save(item);
    }
}
