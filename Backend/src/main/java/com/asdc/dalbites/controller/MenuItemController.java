package com.asdc.dalbites.controller;

import com.asdc.dalbites.model.DAO.MenuItem;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.repository.MenuItemRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/restaurants")
public class MenuItemController {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping("/{restaurantId}/menu")
    public List<MenuItem> getMenuItemsForRestaurant(@PathVariable Long restaurantId){
        return menuItemRepository.findByRestaurantId(restaurantId);
    }
    @PostMapping("/{restaurantId}/item")
    public MenuItem createMenuItemForRestaurant(@PathVariable Long restaurantId, @RequestBody MenuItem menuItem) {
        RestaurantDao restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        menuItem.setRestaurant(restaurant);
        return menuItemRepository.save(menuItem);
    }
}
