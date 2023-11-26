package com.asdc.dalbites.controller;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import com.asdc.dalbites.model.REQUEST.AddMenuItemRequest;
import com.asdc.dalbites.repository.MenuItemRepository;
import com.asdc.dalbites.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling restaurant menu item operations.
 * This class provides endpoints for retrieving a menu, adding a menu item,
 * updating a menu item, and deleting a menu item.
 */
@RestController
@RequestMapping("/api/restaurants")
public class MenuItemController {
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuService menuService;

    /**
     * Retrieves the menu for a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant.
     * @return ResponseEntity with the result of the getMenu operation.
     */
    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<?> getMenu(@PathVariable Long restaurantId) throws Exception{
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
    public ResponseEntity<?> addMenuItem(@ModelAttribute AddMenuItemRequest request) throws Exception {
        try {
            Boolean isAvailable = request.getIsAvailable().equals("1") ? true : false;
            Long restaurantId = Long.parseLong(request.getRestaurantId());
            Double price = Double.parseDouble(request.getPrice());
            Double time = Double.parseDouble(request.getTime());
            String fileName = request.getFile().getOriginalFilename();
            String description = request.getDescription();
            String name = request.getName();

            MenuItemDTO menuItemDTO = new MenuItemDTO(name, description, price, time, isAvailable, fileName, restaurantId);

            List<MenuItemDao> result = menuService.addMenuItem(restaurantId, request.getFile(), menuItemDTO);
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
            Optional<MenuItemDao> tempMenuItem = menuItemRepository.findById(menuItemDTO.getId());
            if(tempMenuItem.isPresent()){
                
                MenuItemDao menuItem = tempMenuItem.get();
                menuItem.setName(menuItemDTO.getName());
                menuItem.setDescription(menuItemDTO.getDescription());
                menuItem.setTime(menuItemDTO.getTime());
                menuItem.setPrice(menuItemDTO.getPrice());
                menuItem.setIs_available(menuItemDTO.getIs_available());
                menuItem.setUpdated_at(new Date());

                final MenuItemDao updatedMenuItem = menuItemRepository.save(menuItem);
                List<MenuItemDao> result = menuService.getMenu(restaurantId);
                return ResponseEntity.status(HttpStatus.CREATED).body(result);

            } else{
                throw new ResourceNotFoundException("Menu item not found on :: " + menuItemDTO.getId());
            }
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
            Optional<MenuItemDao> menuItem = menuItemRepository.findById(menuId);
            if(menuItem.isPresent()){
                MenuItemDao menuItemDao = menuItem.get();
                menuItemDao.setIs_deleted((short)1);
                final MenuItemDao updatedMenuItem = menuItemRepository.save(menuItemDao);
                List<MenuItemDao> result = menuService.getMenu(restaurantId);
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else{
                throw new ResourceNotFoundException("Menu item not found on :: " + menuId);
            }
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
