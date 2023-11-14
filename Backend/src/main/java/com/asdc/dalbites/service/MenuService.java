package com.asdc.dalbites.service;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
public interface MenuService {

    /*
    Redundant Services
    public List<MenuItemDTO> getAllItems(Long restaurantId);
    public MenuItemDTO createMenuItem(Long restaurantId, MenuItemDTO menuItemDTO);
    */

    public List<MenuItemDao> getMenu(Long restaurantId) throws Exception;

    public List<MenuItemDao> addMenuItem(Long restaurantId, MultipartFile file, MenuItemDTO menuItemDTO) throws Exception;

    /*
    Redundant Services
    public HashMap<String, Object> updateMenuItem(Long restaurantId, MenuItemDTO menuItemDTO) throws Exception;

    public HashMap<String, Object> deleteMenuItem(Long menuId, Long restaurantId) throws Exception;
    */
}
