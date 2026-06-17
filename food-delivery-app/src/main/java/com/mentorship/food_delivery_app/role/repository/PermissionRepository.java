package com.mentorship.food_delivery_app.role.repository;

import com.mentorship.food_delivery_app.role.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {}
