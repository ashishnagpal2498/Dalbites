package com.asdc.dalbites.controller;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import com.asdc.dalbites.model.REQUEST.AddMenuItemRequest;
import com.asdc.dalbites.repository.MenuItemRepository;
import com.asdc.dalbites.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuItemController menuItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void getMenu_Success() throws Exception {
        Long restaurantId = 1L;
        List<MenuItemDao> mockMenu = new ArrayList<>();
        when(menuService.getMenu(restaurantId)).thenReturn(mockMenu);
        ResponseEntity<?> response = menuItemController.getMenu(restaurantId);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(mockMenu, response.getBody());
        verify(menuService, times(1)).getMenu(restaurantId);
    }

    @Test
    void getMenu_Exception() throws Exception {
        Long restaurantId = 1L;
        when(menuService.getMenu(restaurantId)).thenThrow(new Exception("Test Exception"));
        ResponseEntity<?> response = menuItemController.getMenu(restaurantId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(menuService, times(1)).getMenu(restaurantId);
    }

    @Test
    void addMenuItem_Success() throws Exception {
        AddMenuItemRequest request = createAddMenuItemRequest();
        List<MenuItemDao> mockMenu = new ArrayList<>();
        when(menuService.addMenuItem(anyLong(), any(MultipartFile.class), any(MenuItemDTO.class))).thenReturn(mockMenu);
        ResponseEntity<?> response = menuItemController.addMenuItem(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(mockMenu, response.getBody());
        verify(menuService, times(1)).addMenuItem(anyLong(), any(MultipartFile.class), any(MenuItemDTO.class));
    }

    @Test
    void addMenuItem_Exception() throws Exception {
        AddMenuItemRequest request = createAddMenuItemRequest();
        when(menuService.addMenuItem(anyLong(), any(MultipartFile.class), any(MenuItemDTO.class))).thenThrow(new Exception("Test Exception"));
        ResponseEntity<?> response = menuItemController.addMenuItem(request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(menuService, times(1)).addMenuItem(anyLong(), any(MultipartFile.class), any(MenuItemDTO.class));
    }

    @Test
    void updateMenuItem_Success() throws Exception {
        Long restaurantId = 1L;
        MenuItemDTO menuItemDTO = createMenuItemDTO();
        MenuItemDao existingMenuItem = createMenuItemDao();
        existingMenuItem.setId(menuItemDTO.getId());
        when(menuItemRepository.findById(menuItemDTO.getId())).thenReturn(Optional.of(existingMenuItem));
        when(menuItemRepository.save(any(MenuItemDao.class))).thenReturn(existingMenuItem);
        when(menuService.getMenu(restaurantId)).thenReturn(new ArrayList<>());
        ResponseEntity<?> response = menuItemController.updateMenuItem(restaurantId, menuItemDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(menuItemRepository, times(1)).findById(menuItemDTO.getId());
        verify(menuItemRepository, times(1)).save(any(MenuItemDao.class));
        verify(menuService, times(1)).getMenu(restaurantId);
    }

    @Test
    void updateMenuItem_MenuItemNotFound() throws Exception {
        Long restaurantId = 1L;
        MenuItemDTO menuItemDTO = createMenuItemDTO();
        when(menuItemRepository.findById(menuItemDTO.getId())).thenReturn(Optional.empty());
        try {
            menuItemController.updateMenuItem(restaurantId, menuItemDTO);
        } catch (ResourceNotFoundException e) {
        }
        verify(menuItemRepository, times(1)).findById(menuItemDTO.getId());
        verify(menuItemRepository, never()).save(any(MenuItemDao.class));
        verify(menuService, never()).getMenu(anyLong());
    }

    @Test
    void deleteMenuItem_Success() throws Exception {
        Long restaurantId = 1L;
        Long menuId = 2L;
        MenuItemDao existingMenuItem = createMenuItemDao();
        when(menuItemRepository.findById(menuId)).thenReturn(Optional.of(existingMenuItem));
        when(menuItemRepository.save(any(MenuItemDao.class))).thenReturn(existingMenuItem);
        when(menuService.getMenu(restaurantId)).thenReturn(new ArrayList<>());
        ResponseEntity<?> response = menuItemController.deleteMenuItem(restaurantId, menuId);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(menuItemRepository, times(1)).findById(menuId);
        verify(menuItemRepository, times(1)).save(any(MenuItemDao.class));
        verify(menuService, times(1)).getMenu(restaurantId);
    }

    private MenuItemDTO createMenuItemDTO() {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setId(1L);
        menuItemDTO.setName("Test Item");
        menuItemDTO.setDescription("Test Description");
        menuItemDTO.setPrice(10.0);
        menuItemDTO.setTime(15.0);
        menuItemDTO.setIs_available(true);
        menuItemDTO.setMenu_image("test.jpg");
        menuItemDTO.setRestaurantId(1L);
        return menuItemDTO;
    }

    private MenuItemDao createMenuItemDao() {
        MenuItemDao menuItemDao = new MenuItemDao();
        menuItemDao.setId(1L);
        menuItemDao.setName("Test Item");
        menuItemDao.setDescription("Test Description");
        menuItemDao.setTime(15.0);
        menuItemDao.setPrice(10.0);
        menuItemDao.setIs_available(true);
        menuItemDao.setMenu_image("test.jpg");  // Corrected property name
        menuItemDao.setRestaurant(new RestaurantDao());  // Replace with an instance of RestaurantDao if needed
        return menuItemDao;
    }


    private AddMenuItemRequest createAddMenuItemRequest() {
        AddMenuItemRequest request = new AddMenuItemRequest();
        request.setIsAvailable("1");
        request.setRestaurantId("1");
        request.setPrice("10.0");
        request.setTime("15.0");
        request.setDescription("Test Description");
        request.setName("Test Item");
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        request.setFile(file);
        return request;
    }
}