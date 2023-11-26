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

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FirebaseFileServiceImpl firebaseFileService;

    @Override
    public List<MenuItemDao> getMenu(Long restaurantId) throws Exception{
        try {
            return menuItemRepository.getMenu(restaurantId);
        } catch (Exception e) {
            throw new Exception("Failed to fetch menu");
        }
    }

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
