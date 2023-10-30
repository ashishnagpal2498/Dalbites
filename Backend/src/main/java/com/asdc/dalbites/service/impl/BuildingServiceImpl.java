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

@Service
public class BuildingServiceImpl implements BuildingService{

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public HashMap<String, Object> addBuilding(BuildingDTO buildingDTO) throws Exception {
        try {
            BuildingDao buildingDao = new BuildingDao(buildingDTO.getName(), buildingDTO.getDescription());
            buildingRepository.save(buildingDao);
            HashMap<String, Object> result = new HashMap<>();
            result.put("message", "New building added successfully");
            return result;
        } catch(DataIntegrityViolationException error) {
            throw new Exception("Failed to add new building");
        }
    }

    @Override
    public List<BuildingDao> getBuildings(){
        return (List<BuildingDao>)buildingRepository.findAll();
    }
}
