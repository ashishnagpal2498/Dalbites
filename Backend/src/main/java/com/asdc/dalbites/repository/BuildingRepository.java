package com.asdc.dalbites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.asdc.dalbites.model.DAO.BuildingDao;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingDao, Long>{

}