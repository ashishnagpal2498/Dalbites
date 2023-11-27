package com.asdc.dalbites.controller;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.RestaurantDTO;
import com.asdc.dalbites.model.REQUEST.SetupRestaurantRequest;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.RestaurantService;
import com.asdc.dalbites.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void getAllRestaurants_NoBuildingParam_Success() {
        String building = "";
        List<RestaurantDao> expectedRestaurants = Arrays.asList(new RestaurantDao(), new RestaurantDao());
        when(restaurantService.getAllRestaurants(Collections.emptyList())).thenReturn(expectedRestaurants);
        ResponseEntity<List<RestaurantDao>> response = restaurantController.getAllRestaurants(building);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRestaurants, response.getBody());
        verify(restaurantService, times(1)).getAllRestaurants(Collections.emptyList());
    }

    @Test
    void getAllRestaurants_WithBuildingParam_Success() {
        String building = "1,2,3";
        List<Long> buildingList = Arrays.asList(1L, 2L, 3L);
        List<RestaurantDao> expectedRestaurants = Arrays.asList(new RestaurantDao(), new RestaurantDao());
        when(restaurantService.getAllRestaurants(buildingList)).thenReturn(expectedRestaurants);
        ResponseEntity<List<RestaurantDao>> response = restaurantController.getAllRestaurants(building);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRestaurants, response.getBody());
        verify(restaurantService, times(1)).getAllRestaurants(buildingList);
    }

    @Test
    void getRestaurantsById_ExistingId_Success() throws ResourceNotFoundException {
        Long restaurantId = 1L;
        RestaurantDao expectedRestaurant = new RestaurantDao();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(expectedRestaurant));
        ResponseEntity<RestaurantDao> response = restaurantController.getRestaurantsById(restaurantId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRestaurant, response.getBody());
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    @Test
    void getRestaurantsById_NonExistingId_ThrowsResourceNotFoundException() {
        Long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> restaurantController.getRestaurantsById(restaurantId));
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    @Test
    void createUser_ValidRestaurantDao_Success() {
        RestaurantDao restaurantDao = new RestaurantDao();
        when(restaurantRepository.save(restaurantDao)).thenReturn(restaurantDao);
        RestaurantDao createdRestaurant = restaurantController.createUser(restaurantDao);
        assertEquals(restaurantDao, createdRestaurant);
        verify(restaurantRepository, times(1)).save(restaurantDao);
    }

    @Test
    void updateRestaurant_ExistingIdAndValidRestaurantDetails_Success() throws ResourceNotFoundException {
        Long restaurantId = 1L;
        RestaurantDao existingRestaurant = new RestaurantDao();
        RestaurantDao restaurantDetails = new RestaurantDao();
        restaurantDetails.setName("Updated Restaurant");
        restaurantDetails.setAddress("Updated Address");
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(restaurantRepository.save(existingRestaurant)).thenReturn(existingRestaurant);
        ResponseEntity<RestaurantDao> response = restaurantController.updateRestaurant(restaurantId, restaurantDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingRestaurant, response.getBody());
        assertEquals("Updated Restaurant", existingRestaurant.getName());
        assertEquals("Updated Address", existingRestaurant.getAddress());
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(restaurantRepository, times(1)).save(existingRestaurant);
    }

    @Test
    void updateRestaurant_NonExistingId_ThrowsResourceNotFoundException() {
        Long restaurantId = 1L;
        RestaurantDao restaurantDetails = new RestaurantDao();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> restaurantController.updateRestaurant(restaurantId, restaurantDetails));
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    void deleteUser_ExistingId_Success() throws Exception {
        Long restaurantId = 1L;
        RestaurantDao existingRestaurant = new RestaurantDao();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(restaurantRepository.save(existingRestaurant)).thenReturn(existingRestaurant);
        ResponseEntity<RestaurantDao> response = restaurantController.deleteUser(restaurantId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingRestaurant, response.getBody());
        assertTrue(existingRestaurant.getIsDeleted() == 1);
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(restaurantRepository, times(1)).save(existingRestaurant);
    }

    @Test
    void deleteUser_NonExistingId_ThrowsResourceNotFoundException() {
        Long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> restaurantController.deleteUser(restaurantId));
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(restaurantRepository, never()).save(any());
    }
}
