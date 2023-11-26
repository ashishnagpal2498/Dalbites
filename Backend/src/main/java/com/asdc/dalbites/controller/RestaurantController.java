package com.asdc.dalbites.controller;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Collections;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.RestaurantDTO;
import com.asdc.dalbites.model.REQUEST.SetupRestaurantRequest;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.RestaurantService;
import com.asdc.dalbites.util.JwtTokenUtil;
import com.asdc.dalbites.util.Constants;

import io.jsonwebtoken.Claims;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling restaurant-related operations.
 * This class provides endpoints for retrieving all restaurants,
 * retrieving a specific restaurant by ID, creating a new restaurant,
 * updating an existing restaurant, deleting a restaurant, and setting up
 * a restaurant account with additional details.
 */
@RestController
@RequestMapping("/api")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * Retrieves all restaurants based on the provided building ID(s).
     *
     * @param building A comma-separated list of building IDs (optional).
     * @return ResponseEntity with the list of restaurants or an empty list.
     */
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDao>> getAllRestaurants(@RequestParam("id") String building) {
    	List<RestaurantDao> restaurants;
    	if(building.equals("")) {
    		 restaurants = restaurantService.getAllRestaurants(Collections.emptyList());
    	}
    	else {
        List<Long> buildingList = Arrays.stream(building.split(",")).map(Long::parseLong).collect(Collectors.toList());
         restaurants = restaurantService.getAllRestaurants(buildingList);
    	}
        return ResponseEntity.ok().body(restaurants);
    }

    /**
     * Retrieves a specific restaurant by its ID.
     *
     * @param restaurantId The ID of the restaurant to be retrieved.
     * @return ResponseEntity with the restaurant details or a not found status.
     * @throws ResourceNotFoundException If the restaurant is not found.
     */
    @GetMapping("/restaurants/{id}")
    public ResponseEntity<RestaurantDao> getRestaurantsById(@PathVariable(value = "id") Long restaurantId)
            throws ResourceNotFoundException {
        Optional<RestaurantDao> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            return ResponseEntity.ok().body(restaurant.get());
        } else{
            throw new ResourceNotFoundException("Restaurant not found on :: " + restaurantId);
        }
    }

    /**
     * Creates a new restaurant.
     *
     * @param restaurant The details of the restaurant to be created.
     * @return The created restaurant.
     */
    @PostMapping("/restaurants")
    public RestaurantDao createUser(@RequestBody RestaurantDao restaurant) {
        return restaurantRepository.save(restaurant);
    }

    /**
     * Updates the details of an existing restaurant.
     *
     * @param restaurantId      The ID of the restaurant to be updated.
     * @param restaurantDetails The updated details of the restaurant.
     * @return ResponseEntity with the updated restaurant details or a not found status.
     * @throws ResourceNotFoundException If the restaurant is not found.
     */
    @PutMapping("/restaurants/{id}")
    public ResponseEntity<RestaurantDao> updateRestaurant(
            @PathVariable(value = "id") Long restaurantId, @RequestBody RestaurantDao restaurantDetails)
            throws ResourceNotFoundException {
        
        Optional<RestaurantDao> tempRestuarant = restaurantRepository.findById(restaurantId);
        if(tempRestuarant.isPresent()){
            RestaurantDao restaurant = tempRestuarant.get();
            restaurant.setName(restaurantDetails.getName());
            restaurant.setAddress(restaurantDetails.getAddress());
            restaurant.setIsActive(restaurantDetails.getIsActive());
            restaurant.setUpdatedAt(new Date());
            final RestaurantDao updatedRestaurant = restaurantRepository.save(restaurant);
            return ResponseEntity.ok(updatedRestaurant);
        } else {
            throw new ResourceNotFoundException("Restaurant not found on :: " + restaurantId);
        }
    }

    /**
     * Deletes a restaurant by setting its status to deleted.
     *
     * @param restaurantId The ID of the restaurant to be deleted.
     * @return ResponseEntity with the deleted restaurant details.
     * @throws Exception If there is an issue during deletion.
     */
    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<RestaurantDao> deleteUser(@PathVariable(value = "id") Long restaurantId) throws Exception {
        Optional<RestaurantDao> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            RestaurantDao restaurantDao =  restaurant.get();
            restaurantDao.setIsDeleted((short) 1);
            final RestaurantDao updatedRestaurant = restaurantRepository.save(restaurantDao);
            return ResponseEntity.ok(updatedRestaurant);
        } else {
            throw new ResourceNotFoundException("Restaurant not found on :: " + restaurantId);
        }
    }

    /**
     * Sets up a new restaurant account with additional details.
     *
     * @param bearerToken   The authentication token in the request header.
     * @param file           The restaurant image file.
     * @param buildingId     The ID of the building associated with the restaurant.
     * @param name           The name of the restaurant.
     * @param description    The description of the restaurant.
     * @param deliveryTime   The delivery time of the restaurant.
     * @return ResponseEntity with the setup details or an error status.
     * @throws Exception If there is an issue during setup.
     */
    @PostMapping("/restaurant/setup")
    public ResponseEntity<?> setupRestaurantAccount(@RequestHeader("Authorization") String bearerToken, @ModelAttribute SetupRestaurantRequest request) throws Exception {
        try {
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(bearerToken.substring(Constants.TOKEN_START_INDEX));
            Long id = Long.valueOf((Integer) claims.get("restaurant_id"));

            Long buildingId = Long.parseLong(request.getBuilding());
            String fileName = request.getFile().getOriginalFilename();
            String name = request.getName();
            String description = request.getDescription();
            String time = request.getDeliveryTime();

            RestaurantDTO restaurantDTO = new RestaurantDTO(id, name, buildingId, description, fileName, time);
            RestaurantDao restaurant = restaurantService.setupRestaurantAccount(request.getFile(), restaurantDTO);
            return ResponseEntity.ok(restaurant);
         } catch (ResourceNotFoundException exception) {
             exception.printStackTrace();
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(exception);
        }
    }
}
