package com.asdc.dalbites.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DAO.RestaurantsDao;

@Service
public interface RestaurantsService {
    public List<RestaurantsDao> getRestaurants(List<Long> buildings);
}
