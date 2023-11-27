package com.asdc.dalbites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asdc.dalbites.model.DAO.UserDao;

@Repository
public interface UserRepository extends JpaRepository<UserDao, String>{
    /**
     * Retrieves a {@link UserDao} entity by its associated login ID.
     *
     * @param login_id The login ID associated with the user to be retrieved.
     * @return The user entity associated with the specified login ID.
     */
    @Query(value = "select * from student where login_id = ?1", nativeQuery = true)
    public UserDao findByLogin_Id(Long login_id);

    /**
     * Retrieves a {@link UserDao} entity by its user ID.
     *
     * @param userId The user ID of the user to be retrieved.
     * @return The user entity associated with the specified user ID.
     */
    UserDao findByUserId(Long userId);
}
