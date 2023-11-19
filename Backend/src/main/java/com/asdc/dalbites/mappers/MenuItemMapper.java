package com.asdc.dalbites.mappers;

import org.springframework.stereotype.Component;
import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;

@Component
public class MenuItemMapper {

    public MenuItemDTO toDTO(MenuItemDao menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setName(menuItem.getName());
        dto.setTime(menuItem.getTime());
        dto.setPrice(menuItem.getPrice());
        dto.setDescription(menuItem.getDescription());
        return dto;
    }

    public MenuItemDao toEntity(MenuItemDTO dto) {
        MenuItemDao menuItem = new MenuItemDao();
        menuItem.setName(dto.getName());
        menuItem.setTime(dto.getTime());
        menuItem.setPrice(dto.getPrice());
        menuItem.setDescription(dto.getDescription());
        return menuItem;
    }
}
