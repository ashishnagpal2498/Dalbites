package com.asdc.dalbites.service;

import com.asdc.dalbites.model.DTO.ReviewDTO;

import java.util.List;


/**
 * Service interface for review-related operations.
 */

public interface ReviewService {
    /**
     * Creates a new review for a restaurant.
     *
     * @param token     The authentication token.
     * @param reviewDTO The data transfer object containing review details.
     * @return A {@link ReviewDTO} representing the newly created review.
     */
    ReviewDTO createReview(String token,ReviewDTO reviewDTO);

    /**
     * Retrieves all reviews for a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant.
     * @return A list of {@link ReviewDTO} representing restaurant reviews.
     */
    List<ReviewDTO> getAllRestaurantReviews(Long restaurantId);

    /**
     * Retrieves all reviews submitted by a user.
     *
     * @param token The authentication token.
     * @return A list of {@link ReviewDTO} representing user reviews.
     */
    List<ReviewDTO> getAllUserReviews(String token);

    /**
     * Retrieves the review submitted by a user for a specific restaurant.
     *
     * @param token        The authentication token.
     * @param restaurantId The ID of the restaurant.
     * @return A {@link ReviewDTO} representing the user's review for the restaurant.
     */
    ReviewDTO getRestaurantReviewByUser(String token, Long restaurantId);

    /**
     * Updates a review submitted by a user.
     *
     * @param updatedReviewDTO The updated review details.
     * @return A {@link ReviewDTO} representing the updated review.
     */
    ReviewDTO updateRestaurantReviewByUser(ReviewDTO updatedReviewDTO);
}
