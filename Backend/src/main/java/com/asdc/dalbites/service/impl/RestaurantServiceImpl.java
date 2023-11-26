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

/**
 * Implementation of the {@link RestaurantService} interface providing methods for managing restaurants.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FirebaseFileServiceImpl firebaseFileService;

    /**
     * Retrieves all restaurants based on the provided list of building IDs.
     *
     * @param buildings A list of building IDs to filter restaurants. If the list is empty, returns all restaurants.
     * @return A list of {@link RestaurantDao} representing the retrieved restaurants.
     */
    @Override
    public List<RestaurantDao> getAllRestaurants(List<Long> buildings) {
    	if(buildings.size() == 0) return  (List<RestaurantDao>)restaurantRepository.findAll();
        else return (List<RestaurantDao>)restaurantRepository.getAll(buildings);
        //throw new UnsupportedOperationException("Unimplemented method 'getAllRestaurants'");
    }

    /**
     * Sets up a new restaurant account using the provided information.
     *
     * @param file                        The image file associated with the restaurant.
     * @param setupRestaurantAccountDTO  The data transfer object containing restaurant setup information.
     * @return A {@link RestaurantDao} representing the newly set up restaurant account.
     * @throws Exception If an error occurs during the setup process.
     * @throws ResourceNotFoundException If the restaurant is not found after setup.
     */
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
