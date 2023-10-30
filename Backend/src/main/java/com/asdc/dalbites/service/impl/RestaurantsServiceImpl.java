package com.asdc.dalbites.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DAO.RestaurantsDao;
import com.asdc.dalbites.repository.RestaurantsRepository;
import com.asdc.dalbites.service.RestaurantsService;

@Service
public class RestaurantsServiceImpl implements RestaurantsService{

    @Autowired
    private RestaurantsRepository restaurantsRepository;

    @Override
    public List<RestaurantsDao> getRestaurants(List<Long> buildings){
        return (List<RestaurantsDao>) restaurantsRepository.getAll(buildings);
        //return (List<RestaurantsDao>)restaurantsRepository.findAll();
    }
}
