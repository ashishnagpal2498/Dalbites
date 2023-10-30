package com.asdc.dalbites.controller;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Collections;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.SetupRestaurantAccountDTO;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.RestaurantService;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;


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
        RestaurantDao restaurant =
                restaurantRepository
                        .findById(restaurantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found on :: " + restaurantId));
        return ResponseEntity.ok().body(restaurant);
    }

    @PostMapping("/restaurants")
    public RestaurantDao createUser(@RequestBody RestaurantDao restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity<RestaurantDao> updateRestaurant(
            @PathVariable(value = "id") Long restaurantId, @RequestBody RestaurantDao restaurantDetails)
            throws ResourceNotFoundException {

        RestaurantDao restaurant =
                restaurantRepository
                        .findById(restaurantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found on :: " + restaurantId));

        restaurant.setName(restaurantDetails.getName());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setIsActive(restaurantDetails.getIsActive());
        restaurant.setName(restaurantDetails.getName());
        restaurant.setUpdatedAt(new Date());
        final RestaurantDao updatedRestaurant = restaurantRepository.save(restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }
    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<RestaurantDao> deleteUser(@PathVariable(value = "id") Long restaurantId) throws Exception {
        RestaurantDao restaurant =
                restaurantRepository
                        .findById(restaurantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found on :: " + restaurantId));

        restaurant.setIsDeleted((short) 1);
        final RestaurantDao updatedRestaurant = restaurantRepository.save(restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @PostMapping("/restaurant/{id}/setup")
    public ResponseEntity<?> setupRestaurantAccount(@PathVariable String id, @RequestParam("file") MultipartFile file, @RequestPart("building") String buildingId, @RequestPart("name") String name, @RequestPart("description") String description) throws Exception {
        try {
            return ResponseEntity.ok(restaurantService.setupRestaurantAccount(file, new SetupRestaurantAccountDTO(Long.parseLong(id), name, Long.parseLong(buildingId), description, file.getOriginalFilename())));
        } catch (ResourceNotFoundException exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(exception);
        }
    }

}
