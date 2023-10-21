package com.asdc.dalbites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asdc.dalbites.model.DAO.RoleDao;


@Repository
public interface RoleRepository extends JpaRepository<RoleDao, Integer>{
    RoleDao findByName(String name);
}
