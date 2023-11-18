package com.asdc.dalbites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asdc.dalbites.model.DAO.UserDao;

@Repository
public interface UserRepository extends JpaRepository<UserDao, String>{
    @Query(value = "select * from student where login_id = ?1", nativeQuery = true)
    public UserDao findByLogin_Id(Long login_id);
}
