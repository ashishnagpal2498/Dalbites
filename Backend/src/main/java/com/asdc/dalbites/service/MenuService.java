package com.asdc.dalbites.service;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Service interface for menu-related operations.
 */
@Service
public interface MenuService {

    /**
     * Retrieves the menu for a given restaurant.
     *
     * @param restaurantId The ID of the restaurant.
     * @return A list of {@link MenuItemDao} representing the menu items.
     * @throws Exception If an error occurs during the retrieval process.
     */
    public List<MenuItemDao> getMenu(Long restaurantId) throws Exception;


    /**
     * Adds a new menu item to the specified restaurant.
     *
     * @param restaurantId The ID of the restaurant.
     * @param file         The image file associated with the menu item.
     * @param menuItemDTO  The data transfer object containing menu item details.
     * @return A list of {@link MenuItemDao} representing the updated menu.
     * @throws Exception If an error occurs during the addition process.
     */
    public List<MenuItemDao> addMenuItem(Long restaurantId, MultipartFile file, MenuItemDTO menuItemDTO) throws Exception;
}
