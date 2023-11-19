package com.asdc.dalbites.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.RestaurantDTO;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.impl.FirebaseFileServiceImpl;
import com.asdc.dalbites.service.impl.RestaurantServiceImpl;

public class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private FirebaseFileServiceImpl firebaseFileService;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testGetAllRestaurants_NoBuildings() {
        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(new RestaurantDao(), new RestaurantDao()));

        List<RestaurantDao> result = restaurantService.getAllRestaurants(new ArrayList<>());

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllRestaurants_WithBuildings() {
        List<Long> buildings = Arrays.asList(1L, 2L);
        when(restaurantRepository.getAll(buildings)).thenReturn(Arrays.asList(new RestaurantDao(), new RestaurantDao()));

        List<RestaurantDao> result = restaurantService.getAllRestaurants(buildings);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testSetupRestaurantAccount() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        RestaurantDTO restaurantDTO = new RestaurantDTO(1L, "name", 1L, "description", "fileName.png", "deliveryTime");
        
        when(firebaseFileService.uploadFile(file)).thenReturn("test-file.txt");
        doNothing().when(restaurantRepository).setupRestaurant("name", "Description", 1L, "testfile.png", 1L, "10 minutes");
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(new RestaurantDao("name", "address")));

        RestaurantDao result = restaurantService.setupRestaurantAccount(file, restaurantDTO);

        assertNotNull(result);
        assertEquals("name", result.getName());
    }

    @Test
    void testSetupRestaurantAccount_UploadFileException() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        RestaurantDTO restaurantDTO = new RestaurantDTO("name", 1L, "description", "fileName.png", "deliveryTime");

        when(firebaseFileService.uploadFile(file)).thenThrow(new FileUploadException("File upload failed"));

        assertThrows(FileUploadException.class, () -> restaurantService.setupRestaurantAccount(file, restaurantDTO));
    }

    @Test
    void testSetupRestaurantAccount_ResourceNotFoundException() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        RestaurantDTO restaurantDTO = new RestaurantDTO("name", 1L, "description", "fileName.png", "deliveryTime");
        restaurantDTO.setId(1L);

        when(firebaseFileService.uploadFile(file)).thenReturn("test-file.txt");
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restaurantService.setupRestaurantAccount(file, restaurantDTO));
    }

}
