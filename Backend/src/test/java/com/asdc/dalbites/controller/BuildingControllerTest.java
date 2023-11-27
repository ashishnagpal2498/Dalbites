package com.asdc.dalbites.controller;

import com.asdc.dalbites.controller.BuildingController;
import com.asdc.dalbites.model.DAO.BuildingDao;
import com.asdc.dalbites.model.DTO.BuildingDTO;
import com.asdc.dalbites.service.BuildingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuildingControllerTest {

    @Mock
    private BuildingService buildingService;

    @InjectMocks
    private BuildingController buildingController;

    @Test
    void addBuilding_Success() throws Exception {
        BuildingDTO buildingDTO = createBuildingDTO();
        when(buildingService.addBuilding(any(BuildingDTO.class))).thenReturn(createExpectedResult());
        ResponseEntity<?> response = buildingController.addBuilding(buildingDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createExpectedResult(), response.getBody());
        verify(buildingService, times(1)).addBuilding(buildingDTO);
    }

    @Test
    void addBuilding_Exception() throws Exception {
        BuildingDTO buildingDTO = createBuildingDTO();
        when(buildingService.addBuilding(any(BuildingDTO.class))).thenThrow(new Exception("Test Exception"));
        ResponseEntity<?> response = buildingController.addBuilding(buildingDTO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(buildingService, times(1)).addBuilding(buildingDTO);
    }


    private BuildingDTO createBuildingDTO() {
        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setName("Test Building");
        buildingDTO.setDescription("Test Description");
        return buildingDTO;
    }

    private HashMap<String, Object> createExpectedResult() {
        HashMap<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("message", "Building added successfully");
        return expectedResult;
    }

    private List<BuildingDao> createExpectedBuildings() {
        List<BuildingDao> buildings = new ArrayList<>();
        BuildingDao building1 = new BuildingDao();
        building1.setId(1L);
        building1.setName("Building 1");
        building1.setDescription("Description 1");
        buildings.add(building1);
        BuildingDao building2 = new BuildingDao();
        building2.setId(2L);
        building2.setName("Building 2");
        building2.setDescription("Description 2");
        buildings.add(building2);
        return buildings;
    }

}
