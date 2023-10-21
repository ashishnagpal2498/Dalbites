package com.asdc.dalbites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.dalbites.model.DAO.LoginDao;

@Repository
public interface LoginRepository extends JpaRepository<LoginDao, Long>{
    LoginDao findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "update login set is_verified = 1 where username = ?1", nativeQuery = true)
    int setIsVerified(String username);

    @Modifying
    @Transactional
    @Query(value = "update login set password = ?2 where username = ?1", nativeQuery = true)
    int setPassword(String usename, String password);
}
