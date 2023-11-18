package com.asdc.dalbites.service;

import com.asdc.dalbites.model.DTO.ReviewDTO;

public interface ReviewService {
    ReviewDTO createReview(String token,ReviewDTO reviewDTO);
    void getAllRestaurantReviews();
    void getAllUserReviews();
}
