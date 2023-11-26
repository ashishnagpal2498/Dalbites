package com.asdc.dalbites.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.RestaurantDTO;

/**
 * Service interface for restaurant-related operations.
 */
@Service
public interface RestaurantService {
    /**
     * Retrieves a list of all restaurants based on the provided building IDs.
     *
     * @param buildings A list of building IDs to filter the restaurants.
     * @return A list of {@link RestaurantDao} representing the restaurants.
     */
    public List<RestaurantDao> getAllRestaurants(List<Long> buildings);
    /**
     * Sets up a new restaurant account with the provided details.
     *
     * @param file                       The image file for the restaurant.
     * @param setupRestaurantAccountDTO  The data transfer object containing restaurant setup details.
     * @return A {@link RestaurantDao} representing the newly created restaurant.
     * @throws Exception If an error occurs during restaurant account setup.
     */
    public RestaurantDao setupRestaurantAccount(MultipartFile file, RestaurantDTO setupRestaurantAccountDTO) throws Exception;

}
