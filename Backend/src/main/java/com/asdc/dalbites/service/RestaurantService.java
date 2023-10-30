package com.asdc.dalbites.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.SetupRestaurantAccountDTO;

@Service
public interface RestaurantService {

    public List<RestaurantDao> getAllRestaurants(List<Long> buildings);

    public RestaurantDao setupRestaurantAccount(MultipartFile file, SetupRestaurantAccountDTO setupRestaurantAccountDTO) throws Exception;

}
