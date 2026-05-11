package com.mentorship.food_delivery_app.restaurant.service;

import com.mentorship.food_delivery_app.restaurant.model.MenuItem;
import com.mentorship.food_delivery_app.restaurant.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItem getMenuItem(UUID id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu Item is not found"));
    }
}
