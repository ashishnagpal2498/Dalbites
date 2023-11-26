package com.asdc.dalbites.service.impl;

import java.util.List;
import java.util.Optional;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.RestaurantDTO;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.RestaurantService;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FirebaseFileServiceImpl firebaseFileService;

    @Override
    public List<RestaurantDao> getAllRestaurants(List<Long> buildings) {
    	if(buildings.size() == 0) return  (List<RestaurantDao>)restaurantRepository.findAll();
        else return (List<RestaurantDao>)restaurantRepository.getAll(buildings);
        //throw new UnsupportedOperationException("Unimplemented method 'getAllRestaurants'");
    }

    @Override
    public RestaurantDao setupRestaurantAccount(MultipartFile file, RestaurantDTO setupRestaurantAccountDTO) throws Exception {
        try {
            String filename = firebaseFileService.uploadFile(file);
            setupRestaurantAccountDTO.setFileName(filename);

            String name = setupRestaurantAccountDTO.getName();
            String description = setupRestaurantAccountDTO.getDescription();
            Long buildingId = setupRestaurantAccountDTO.getBuilding_id();
            String fileName = setupRestaurantAccountDTO.getFileName();
            Long id = setupRestaurantAccountDTO.getId();
            String time = setupRestaurantAccountDTO.getDeliveryTime();

            restaurantRepository.setupRestaurant(name, description, buildingId, fileName, id, time);
            Optional<RestaurantDao> restaurantDao = restaurantRepository.findById(setupRestaurantAccountDTO.getId());
            if (restaurantDao.isPresent()) {
                return restaurantDao.get();
            } else {
                throw new ResourceNotFoundException("Restaurant Not Found");
            }
        } catch(Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

}
