package com.asdc.dalbites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.dalbites.model.DAO.LoginDao;

@Repository
public interface LoginRepository extends JpaRepository<LoginDao, Long>{

    /**
     * Retrieves a {@link LoginDao} entity by username.
     *
     * @param username The username associated with the login.
     * @return The login entity with the specified username, or {@code null} if not found.
     */
    LoginDao findByUsername(String username);

    /**
     * Sets the verification status of a login entity to 'verified'.
     *
     * @param username The username associated with the login.
     * @return The number of affected rows in the database.
     */
    @Modifying
    @Transactional
    @Query(value = "update login set is_verified = 1 where username = ?1", nativeQuery = true)
    int setIsVerified(String username);

    /**
     * Sets the password for a login entity.
     *
     * @param usename The username associated with the login.
     * @param password The new password to be set.
     * @return The number of affected rows in the database.
     */
    @Modifying
    @Transactional
    @Query(value = "update login set password = ?2 where username = ?1", nativeQuery = true)
    int setPassword(String usename, String password);
}
