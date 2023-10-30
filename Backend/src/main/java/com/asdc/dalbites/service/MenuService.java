package com.asdc.dalbites.service;

import com.asdc.dalbites.model.DAO.MenuItem;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuService {

    public List<MenuItemDTO> getAllItems(Long restaurantId);
    public MenuItemDTO createMenuItem(Long restaurantId, MenuItemDTO menuItemDTO);
}
