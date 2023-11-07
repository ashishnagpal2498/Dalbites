package com.asdc.dalbites.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asdc.dalbites.model.DAO.RestaurantsDao;
import com.asdc.dalbites.service.RestaurantsService;

@RestController
@RequestMapping("/api")
public class RestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;

    @GetMapping("/get-restaurants")
    public List<RestaurantsDao> getRestaurants(@RequestBody HashMap<String, List<Long>> buildings){
        List<Long> buildingList= buildings.get("id");
        return restaurantsService.getRestaurants(buildingList);
    }
}
