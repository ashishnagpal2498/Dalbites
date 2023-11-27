package com.asdc.dalbites.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DAO.BuildingDao;
import com.asdc.dalbites.model.DTO.BuildingDTO;
import com.asdc.dalbites.repository.BuildingRepository;
import com.asdc.dalbites.service.BuildingService;

/**
 * Implementation of the {@link BuildingService} interface providing methods to manage buildings.
 */
@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    /**
     * Adds a new building to the system.
     *
     * @param buildingDTO The data transfer object containing information about the building.
     * @return A {@code HashMap} containing a success message if the building is added successfully.
     * @throws Exception If there is a {@link DataIntegrityViolationException}, indicating a failure to add the building.
     */
    @Override
    public HashMap<String, Object> addBuilding(BuildingDTO buildingDTO) throws Exception {
        try {
            BuildingDao buildingDao = new BuildingDao(buildingDTO.getName(), buildingDTO.getDescription());
            buildingRepository.save(buildingDao);
            HashMap<String, Object> result = new HashMap<>();
            result.put("message", "New building added successfully");
            return result;
        } catch (DataIntegrityViolationException error) {
            throw new Exception("Failed to add new building");
        }
    }

    /**
     * Retrieves a list of all buildings in the system.
     *
     * @return A {@code List} of {@link BuildingDao} objects representing all buildings.
     */
    @Override
    public List<BuildingDao> getBuildings() {
        return (List<BuildingDao>) buildingRepository.findAll();
    }
}
