package com.asdc.dalbites.service;

import com.asdc.dalbites.model.DTO.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(String token,ReviewDTO reviewDTO);
    List<ReviewDTO> getAllRestaurantReviews(Long restaurantId);
    List<ReviewDTO> getAllUserReviews(String token);
    ReviewDTO getRestaurantReviewByUser(String token, Long restaurantId);
    ReviewDTO updateRestaurantReviewByUser(ReviewDTO updatedReviewDTO);
}
