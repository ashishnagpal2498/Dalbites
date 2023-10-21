package com.asdc.dalbites.service.impl;

import java.util.List;

import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.service.RestaurantService;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Override
    public List<RestaurantDao> getAllRestaurants() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllRestaurants'");
    }

}
