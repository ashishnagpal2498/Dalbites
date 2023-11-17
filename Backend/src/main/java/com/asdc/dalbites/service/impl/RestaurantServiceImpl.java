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
            String fileName = firebaseFileService.uploadFile(file);
            setupRestaurantAccountDTO.setFileName(fileName);
            restaurantRepository.setupRestaurant(setupRestaurantAccountDTO.getName(), setupRestaurantAccountDTO.getDescription(), setupRestaurantAccountDTO.getBuilding_id(), setupRestaurantAccountDTO.getFileName(), setupRestaurantAccountDTO.getId());
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
