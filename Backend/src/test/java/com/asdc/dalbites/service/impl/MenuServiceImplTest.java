package com.asdc.dalbites.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import com.asdc.dalbites.repository.MenuItemRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.impl.FirebaseFileServiceImpl;
import com.asdc.dalbites.service.impl.MenuServiceImpl;

public class MenuServiceImplTest {
    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private FirebaseFileServiceImpl firebaseFileServiceImpl;

    @InjectMocks
    private MenuServiceImpl menuService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMenu() throws Exception {
        Long restaurantId = 1L;
        List<MenuItemDao> expectedMenu = new ArrayList<>();
        when(menuItemRepository.getMenu(restaurantId)).thenReturn(expectedMenu);

        List<MenuItemDao> actualMenu = menuService.getMenu(restaurantId);

        assertEquals(expectedMenu, actualMenu);
    }

    @Test
    public void testAddMenuItem() throws Exception {
        Long restaurantId = 1L;
        MultipartFile file = mock(MultipartFile.class);
        MenuItemDTO menuItemDTO = new MenuItemDTO("name", "description", 7.0D, 25.0D, true, "fileName.jpg", 1L);

        //RestaurantDao restaurant = restaurantRepository.getRestaurant(restaurantId);

        when(firebaseFileServiceImpl.uploadFile(file)).thenReturn("testFile.txt");
        //when(restaurantRepository.getRestaurant(restaurantId)).thenReturn(new RestaurantDao("name", "address"));

        List<MenuItemDao> actualMenu = menuService.getMenu(restaurantId);

        List<MenuItemDao> result = menuService.addMenuItem(restaurantId, file, menuItemDTO);

        Integer actualMenuSize = actualMenu.size();
        Integer resultSize = result.size();
        assertNotNull(result);
        assertEquals(actualMenuSize, resultSize);
    }
}
