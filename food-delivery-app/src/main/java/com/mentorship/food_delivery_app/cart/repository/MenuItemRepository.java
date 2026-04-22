package com.mentorship.ecommerce_app.cart.repository;


import com.mentorship.ecommerce_app.cart.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    Optional<MenuItem> findById(UUID id);
}
