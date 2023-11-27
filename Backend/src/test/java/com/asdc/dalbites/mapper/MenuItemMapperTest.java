package com.asdc.dalbites.mapper;
import com.asdc.dalbites.mappers.MenuItemMapper;
import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MenuItemMapperTest {

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Test
    void toDTO() {
        MenuItemDao menuItemDao = createSampleMenuItemDao();
        MenuItemDTO menuItemDTO = menuItemMapper.toDTO(menuItemDao);
        assertEquals(menuItemDao.getName(), menuItemDTO.getName());
        assertEquals(menuItemDao.getTime(), menuItemDTO.getTime());
        assertEquals(menuItemDao.getPrice(), menuItemDTO.getPrice());
        assertEquals(menuItemDao.getDescription(), menuItemDTO.getDescription());
    }

    @Test
    void toEntity() {
        MenuItemDTO menuItemDTO = createSampleMenuItemDTO();
        MenuItemDao menuItemDao = menuItemMapper.toEntity(menuItemDTO);
        assertEquals(menuItemDTO.getName(), menuItemDao.getName());
        assertEquals(menuItemDTO.getTime(), menuItemDao.getTime());
        assertEquals(menuItemDTO.getPrice(), menuItemDao.getPrice());
        assertEquals(menuItemDTO.getDescription(), menuItemDao.getDescription());
    }

    private MenuItemDao createSampleMenuItemDao() {
        MenuItemDao menuItemDao = new MenuItemDao();
        menuItemDao.setName("Sample Item");
        menuItemDao.setPrice(10.99);
        menuItemDao.setDescription("A description for the sample item.");
        return menuItemDao;
    }

    private MenuItemDTO createSampleMenuItemDTO() {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setName("Sample Item");
        menuItemDTO.setPrice(10.99);
        menuItemDTO.setDescription("A description for the sample item.");
        return menuItemDTO;
    }
}
