package com.asdc.dalbites.repository;

import com.asdc.dalbites.model.DAO.ReviewDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDao, Long> {

    /**
     * Retrieves a list of {@link ReviewDao} entities based on a restaurant ID.
     *
     * @param restaurantId The ID of the restaurant for which reviews are retrieved.
     * @return A list of reviews associated with the specified restaurant ID.
     */
    List<ReviewDao> findByRestaurantId(Long restaurantId);

    /**
     * Retrieves a list of {@link ReviewDao} entities based on a user ID.
     *
     * @param userId The ID of the user for whom reviews are retrieved.
     * @return A list of reviews associated with the specified user ID.
     */
    List<ReviewDao> findByUser_UserId(Long userId);


    /**
     * Retrieves an optional {@link ReviewDao} entity by both user and restaurant IDs.
     *
     * @param userId       The ID of the user associated with the review.
     * @param restaurantId The ID of the restaurant associated with the review.
     * @return An optional containing the retrieved review entity, or empty if not found.
     */
    Optional<ReviewDao> findByUser_UserIdAndRestaurant_Id(Long userId, Long restaurantId);
}