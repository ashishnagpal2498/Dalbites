package com.asdc.dalbites.repository;

import com.asdc.dalbites.model.DAO.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurantId(Long restaurantId);
    List<MenuItem> findByRestaurantIdAndIsDeletedFalse(Long restaruantId);
}
