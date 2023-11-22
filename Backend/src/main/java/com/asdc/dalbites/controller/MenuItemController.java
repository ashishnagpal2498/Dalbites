package com.asdc.dalbites.controller;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import com.asdc.dalbites.repository.MenuItemRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.MenuService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Controller class for handling restaurant menu item operations.
 * This class provides endpoints for retrieving a menu, adding a menu item,
 * updating a menu item, and deleting a menu item.
 */
@RestController
@RequestMapping("/api/restaurants")
public class MenuItemController {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuService menuService;

    /*
    Redundant API's (for View Menu User Story)

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
    */

    /**
     * Retrieves the menu for a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant.
     * @return ResponseEntity with the result of the getMenu operation.
     */
    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<?> getMenu(@PathVariable Long restaurantId) throws Exception{
        //return menuItemRepository.getMenu(restaurantId);
        try {
            List<MenuItemDao> result = menuService.getMenu(restaurantId);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }

    /**
     * Adds a new menu item to the specified restaurant.
     *
     * @param file The image file for the menu item.
     * @param name The name of the menu item.
     * @param description The description of the menu item.
     * @param price The price of the menu item.
     * @param time The preparation time of the menu item.
     * @param is_available The availability status of the menu item.
     * @param restaurant_id The ID of the restaurant.
     * @return ResponseEntity with the result of the addMenuItem operation.
     */
    @PostMapping("/{restaurantId}/add-menu-item")
    public ResponseEntity<?> addMenuItem(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") String price, @RequestParam("time") String time, @RequestParam("is_available") String is_available, @RequestParam("restaurant_id") String restaurant_id) throws Exception{
        try {
            Boolean is_available_boolean;
            if(is_available.equals("true")) is_available_boolean = true;
            else is_available_boolean = false;

            //HashMap<String, Object> result = menuService.addMenuItem(Long.parseLong(restaurant_id), file, new MenuItemDTO(name, description, Double.parseDouble(price), Double.parseDouble(time), is_available_boolean , file.getOriginalFilename(), Long.parseLong(restaurant_id)));
            List<MenuItemDao> result = menuService.addMenuItem(Long.parseLong(restaurant_id), file, new MenuItemDTO(name, description, Double.parseDouble(price), Double.parseDouble(time), is_available_boolean , file.getOriginalFilename(), Long.parseLong(restaurant_id)));
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }

    /**
     * Updates an existing menu item for the specified restaurant.
     *
     * @param restaurantId The ID of the restaurant.
     * @param menuItemDTO The data transfer object containing updated menu item information.
     * @return ResponseEntity with the result of the updateMenuItem operation.
     */
    @PutMapping("/{restaurantId}/update-menu-item")
    public ResponseEntity<?> updateMenuItem(@PathVariable Long restaurantId, @RequestBody MenuItemDTO menuItemDTO) throws Exception{
        try {
            MenuItemDao menuItem =
                menuItemRepository
                        .findById(menuItemDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Menu item not found on :: " + menuItemDTO.getId()));

            menuItem.setName(menuItemDTO.getName());
            menuItem.setDescription(menuItemDTO.getDescription());
            menuItem.setTime(menuItemDTO.getTime());
            menuItem.setPrice(menuItemDTO.getPrice());
            menuItem.setIs_available(menuItemDTO.getIs_available());
            menuItem.setUpdated_at(new Date());

            final MenuItemDao updatedMenuItem = menuItemRepository.save(menuItem);
            List<MenuItemDao> result = menuService.getMenu(restaurantId);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);

            // HashMap<String, Object> result = menuService.updateMenuItem(restaurantId, menuItemDTO);
            // return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception error) {
            System.out.println(error);
            return ResponseEntity.internalServerError().body(error);
        }
    }

    /**
     * Deletes a menu item from the specified restaurant.
     *
     * @param restaurantId The ID of the restaurant.
     * @param menuId The ID of the menu item to be deleted.
     * @return ResponseEntity with the result of the deleteMenuItem operation.
     */
    @DeleteMapping("/{restaurantId}/delete-menu-item/{menuId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable(value="restaurantId") Long restaurantId, @PathVariable(value="menuId") Long menuId) throws Exception{
        try {

            MenuItemDao menuItem =
                menuItemRepository
                        .findById(menuId)
                        .orElseThrow(() -> new ResourceNotFoundException("Menu item not found on :: " + menuId));

            menuItem.setIs_deleted((short)1);
            final MenuItemDao updatedMenuItem = menuItemRepository.save(menuItem);
            List<MenuItemDao> result = menuService.getMenu(restaurantId);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
            
            // HashMap<String, Object> result = menuService.deleteMenuItem(menuId, restaurantId);
            // return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
