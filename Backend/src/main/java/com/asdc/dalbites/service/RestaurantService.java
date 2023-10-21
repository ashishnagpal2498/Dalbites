package com.asdc.dalbites.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DAO.RestaurantDao;

@Service
public interface RestaurantService {

    public List<RestaurantDao> getAllRestaurants();

}
