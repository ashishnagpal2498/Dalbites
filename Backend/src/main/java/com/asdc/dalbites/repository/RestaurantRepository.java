package com.asdc.dalbites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asdc.dalbites.model.DAO.RestaurantDao;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantDao, Long> {
    @Query(value = "select * from restaurant_owner where login_id = ?1", nativeQuery = true)
    public RestaurantDao findByLogin_Id(Long login_id);
}
