package com.asdc.dalbites.repository;

import com.asdc.dalbites.model.DAO.ReviewDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDao, Long> {
    List<ReviewDao> findByRestaurantId(Long restaurantId);
    List<ReviewDao> findByUser_UserId(Long userId);
}