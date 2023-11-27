package com.asdc.dalbites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.asdc.dalbites.model.DAO.BuildingDao;

/**
 * Repository interface for accessing and managing {@link BuildingDao} entities
 * in the database. This interface extends {@link JpaRepository} to provide
 * CRUD (Create, Read, Update, Delete) operations.
 * <p>
 * The generic types used in {@code JpaRepository<BuildingDao, Long>} specify that
 * this repository deals with entities of type {@link BuildingDao}, and the primary
 * key of the entity is of type {@code Long}.
 * </p>
 */
@Repository
public interface BuildingRepository extends JpaRepository<BuildingDao, Long>{

}