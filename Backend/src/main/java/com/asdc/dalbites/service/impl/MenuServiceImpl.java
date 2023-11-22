package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.mappers.MenuItemMapper;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.repository.MenuItemRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    /*
    Redundant Service Impl

    @Autowired
    private MenuItemMapper menuItemMapper; // Uncomment this line if you intend to use MenuItemMapper

    @Override
    public MenuItemDTO createMenuItem(Long restaurantId, MenuItemDTO menuItemDTO) {
        MenuItemDao menuItem = menuItemMapper.toEntity(menuItemDTO);

        menuItem.setRestaurant(restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found")));
        menuItem.setCreated_at(new Date()); // Corrected method name
        menuItem.setIs_deleted(0); // Corrected attribute name
        menuItem.setUpdated_at(new Date()); // Corrected method name

        menuItem = menuItemRepository.save(menuItem);

        return menuItemMapper.toDTO(menuItem);
    }

    @Override
    public List<MenuItemDTO> getAllItems(Long restaurantId) {
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        List<MenuItem> menuItemList = menuItemRepository.findByRestaurantIdAndIsDeletedFalse(restaurantId);

        return menuItemList.stream()
                .map(menuItemMapper::toDTO)
                .collect(Collectors.toList());
    }
    */

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
    //public HashMap<String, Object> addMenuItem(MenuItemDTO menuItemDTO) throws Exception{
        try {
            Long restaurant_id = menuItemDTO.getRestaurantId();
            String fileName = firebaseFileService.uploadFile(file);
            menuItemDTO.setMenuImage(fileName);
            RestaurantDao restaurantDAO = restaurantRepository.getRestaurant(restaurant_id);
            MenuItemDao menuItem = new MenuItemDao(menuItemDTO.getName(), menuItemDTO.getDescription(), menuItemDTO.getPrice(), menuItemDTO.getTime(), menuItemDTO.getIsAvailable(), menuItemDTO.getMenuImage(), restaurantDAO);
            //MenuItem menuItem = new MenuItem(menuItemDTO.getName(), menuItemDTO.getDescription(), menuItemDTO.getPrice(), menuItemDTO.getTime(), menuItemDTO.getIsAvailable(), "", restaurantDAO);
            menuItemRepository.save(menuItem);
            // HashMap<String, Object> result = new HashMap<>();
            // result.put("message", "New menu item added successfully");
            List<MenuItemDao> data = menuItemRepository.getMenu(restaurantId);
            //result.put("data", data);
            return data;

        } catch (Exception error) {
            throw new Exception("Failed to add menu item");
        }
    }

    /*
    Redundant Service Impl
    @Override
    public HashMap<String, Object> updateMenuItem(Long restaurantId, MenuItemDTO menuItemDTO) throws Exception{
        try {
            menuItemRepository.updateMenuItem(menuItemDTO.getId(), menuItemDTO.getName(), menuItemDTO.getDescription(), menuItemDTO.getTime(), menuItemDTO.getPrice(), menuItemDTO.getIsAvailable());
            HashMap<String, Object> result = new HashMap<>();
            result.put("message", "Menu item updated successfully");
            List<MenuItemDao> data = menuItemRepository.getMenu(restaurantId);
            result.put("data", data);
            return result;

        } catch (Exception error) {
            throw new Exception("Failed to update menu item");
        }
    }

    @Override
    public HashMap<String, Object> deleteMenuItem(Long menuId, Long restaurantId) throws Exception{
        try {
            menuItemRepository.deleteMenuItem(menuId);
            HashMap<String, Object> result = new HashMap<>();
            result.put("message", "Menu item deleted successfully");
            List<MenuItemDao> data = menuItemRepository.getMenu(restaurantId);
            result.put("data", data);
            return result;
        } catch (Exception error) {
            throw new Exception("Failed to delete menu item");
        }
    }
    */
}
