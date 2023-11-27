package com.asdc.dalbites.mappers;

import org.springframework.stereotype.Component;
import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;

/**
 * Component class responsible for mapping between MenuItemDao and MenuItemDTO.
 */
@Component
public class MenuItemMapper {

    /**
     * Converts MenuItemDao to MenuItemDTO.
     *
     * @param menuItem The MenuItemDao object to be converted.
     * @return The corresponding MenuItemDTO object.
     */
    public MenuItemDTO toDTO(MenuItemDao menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setName(menuItem.getName());
        dto.setTime(menuItem.getTime());
        dto.setPrice(menuItem.getPrice());
        dto.setDescription(menuItem.getDescription());
        return dto;
    }

    /**
     * Converts MenuItemDTO to MenuItemDao.
     *
     * @param dto The MenuItemDTO object to be converted.
     * @return The corresponding MenuItemDao object.
     */
    public MenuItemDao toEntity(MenuItemDTO dto) {
        MenuItemDao menuItem = new MenuItemDao();
        menuItem.setName(dto.getName());
        menuItem.setTime(dto.getTime());
        menuItem.setPrice(dto.getPrice());
        menuItem.setDescription(dto.getDescription());
        return menuItem;
    }
}
