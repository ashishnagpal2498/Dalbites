package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.model.DTO.MenuItemDTO;
import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.repository.MenuItemRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Implementation of the {@link MenuService} interface providing methods for managing restaurant menus.
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FirebaseFileServiceImpl firebaseFileService;

    /**
     * Retrieves the menu for a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant for which the menu is retrieved.
     * @return A list of {@link MenuItemDao} representing the menu items for the restaurant.
     * @throws Exception If there is a failure in fetching the menu.
     */
    @Override
    public List<MenuItemDao> getMenu(Long restaurantId) throws Exception{
        try {
            return menuItemRepository.getMenu(restaurantId);
        } catch (Exception e) {
            throw new Exception("Failed to fetch menu");
        }
    }

    /**
     * Adds a new menu item to the restaurant's menu.
     *
     * @param restaurantId The ID of the restaurant to which the menu item is added.
     * @param file         The image file associated with the menu item.
     * @param menuItemDTO  The data transfer object containing menu item information.
     * @return A list of {@link MenuItemDao} representing the updated menu items for the restaurant.
     * @throws Exception If there is a failure in adding the menu item.
     */
    @Override
    public List<MenuItemDao> addMenuItem(Long restaurantId, MultipartFile file, MenuItemDTO menuItemDTO) throws Exception{
        try {
            Long restaurant_id = menuItemDTO.getRestaurantId();
            String fileName = firebaseFileService.uploadFile(file);
            menuItemDTO.setMenuImage(fileName);
            RestaurantDao restaurantDAO = restaurantRepository.getRestaurant(restaurant_id);
            String name = menuItemDTO.getName();
            String description = menuItemDTO.getDescription();
            Double price = menuItemDTO.getPrice();
            Double time = menuItemDTO.getTime();
            Boolean isAvailable = menuItemDTO.getIsAvailable();
            String menuImage = menuItemDTO.getMenuImage();
            MenuItemDao menuItem = new MenuItemDao(name, description, price, time, isAvailable, menuImage, restaurantDAO);
            menuItemRepository.save(menuItem);
            List<MenuItemDao> data = menuItemRepository.getMenu(restaurantId);
            return data;

        } catch (Exception error) {
            throw new Exception("Failed to add menu item");
        }
    }
}
