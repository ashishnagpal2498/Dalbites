package com.asdc.dalbites.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asdc.dalbites.model.DAO.BuildingDao;
import com.asdc.dalbites.model.DTO.BuildingDTO;
import com.asdc.dalbites.service.BuildingService;

/**
 * Controller class for handling building-related operations.
 * This class provides endpoints for adding a building and retrieving a list of buildings.
 */
@RestController
@RequestMapping("/api")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    /**
     * Handles the addition of a new building.
     *
     * @param buildingDTO The data transfer object containing building information.
     * @return ResponseEntity with the result of the add-building operation.
     */
    @PostMapping("/add-building")
    public ResponseEntity<?> addBuilding(@RequestBody BuildingDTO buildingDTO) throws Exception {
        try {
            HashMap<String, Object> result = buildingService.addBuilding(buildingDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch(Exception error) {
			return ResponseEntity.internalServerError().body(error);
		}
    }

    /**
     * Handles the retrieval of a list of buildings.
     *
     * @return List of BuildingDao objects representing the buildings.
     */
    @GetMapping("/get-buildings")
    public List<BuildingDao> getBuildings(){
        return buildingService.getBuildings();
    }
}
