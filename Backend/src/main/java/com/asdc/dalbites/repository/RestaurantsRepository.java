package com.asdc.dalbites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.asdc.dalbites.model.DAO.RestaurantsDao;

@Repository
public interface RestaurantsRepository extends JpaRepository<RestaurantsDao, Long>{
    @Query(value = "select * from restaurant where building_id in (:ids)", nativeQuery = true)
    public List<RestaurantsDao> getAll(List<Long> ids);
}
