package com.asdc.dalbites.repository;

import com.asdc.dalbites.model.DAO.ReviewDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDao, Long> {
}