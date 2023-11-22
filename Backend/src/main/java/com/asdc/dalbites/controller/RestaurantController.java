package com.asdc.dalbites.controller;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Collections;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.RestaurantDTO;
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

@RestController
@RequestMapping("/api")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


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

    @PostMapping("/restaurants")
    public RestaurantDao createUser(@RequestBody RestaurantDao restaurant) {
        return restaurantRepository.save(restaurant);
    }

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

    @PostMapping("/restaurant/setup")
    public ResponseEntity<?> setupRestaurantAccount(@RequestHeader("Authorization") String bearerToken, @RequestParam("file") MultipartFile file, @RequestPart("building") String buildingId, @RequestPart("name") String name, @RequestPart("description") String description, @RequestPart("delivery_time") String deliveryTime) throws Exception {
        try {
        	Claims claims = jwtTokenUtil.getAllClaimsFromToken(bearerToken.substring(Constants.TOKEN_START_INDEX));
        	Long id = Long.valueOf((Integer) claims.get("restaurant_id"));

            Long building_id = Long.parseLong(buildingId);
            String file_name = file.getOriginalFilename();
            RestaurantDTO restaurantDTO = new RestaurantDTO(id, name, building_id, description, file_name, deliveryTime);
            RestaurantDao restaurant = restaurantService.setupRestaurantAccount(file, restaurantDTO);
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
