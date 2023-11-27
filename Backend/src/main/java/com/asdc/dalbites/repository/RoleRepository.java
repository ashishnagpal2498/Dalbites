package com.asdc.dalbites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asdc.dalbites.model.DAO.RoleDao;


@Repository
public interface RoleRepository extends JpaRepository<RoleDao, Integer>{
    /**
     * Retrieves a {@link RoleDao} entity by its name.
     *
     * @param name The name of the role to be retrieved.
     * @return The role entity associated with the specified name.
     */
    RoleDao findByName(String name);
}
