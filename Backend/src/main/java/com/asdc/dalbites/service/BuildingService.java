package com.asdc.dalbites.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DAO.BuildingDao;
import com.asdc.dalbites.model.DTO.BuildingDTO;

@Service
public interface BuildingService {
    public HashMap<String, Object> addBuilding(BuildingDTO buildingDTO) throws Exception;

    public List<BuildingDao> getBuildings();
}
