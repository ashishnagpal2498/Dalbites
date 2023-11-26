package com.asdc.dalbites.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DAO.BuildingDao;
import com.asdc.dalbites.model.DTO.BuildingDTO;


/**
 * Service interface for managing building information.
 */
@Service
public interface BuildingService {

    /**
     * Adds a new building to the system.
     *
     * @param buildingDTO The {@link BuildingDTO} containing information about the building to be added.
     * @return A {@link HashMap} containing information about the operation result.
     * @throws Exception If an error occurs during the building addition process.
     */
    public HashMap<String, Object> addBuilding(BuildingDTO buildingDTO) throws Exception;

    /**
     * Retrieves a list of all buildings in the system.
     *
     * @return A list of {@link BuildingDao} representing all buildings in the system.
     */
    public List<BuildingDao> getBuildings();
}
