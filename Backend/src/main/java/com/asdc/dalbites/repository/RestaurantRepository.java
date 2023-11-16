package com.asdc.dalbites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.dalbites.model.DAO.RestaurantDao;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantDao, Long> {

    @Query(value = "select * from restaurant_owner where login_id = ?1", nativeQuery = true)
    public RestaurantDao findByLogin_Id(Long login_id);

    @Modifying
    @Transactional
    @Query(value = "update restaurant_owner set restaurant_name = ?1, restaurant_description = ?2, building_id = ?3, restaurant_image = ?4 where restaurant_id = ?5", nativeQuery = true)
    void setupRestaurant(String name, String description, Long buildingId, String image, Long id);

    @Query(value = "select * from restaurant_owner where building_id in (:ids)", nativeQuery = true)
    public List<RestaurantDao> getAll(List<Long> ids);

    @Query(value = "select * from restaurant_owner where restaurant_id = ?1", nativeQuery = true)
    public RestaurantDao getRestaurant(Long id);
}
