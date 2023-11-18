package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.model.DTO.ReviewDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;

public class ReviewServiceImplTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void testCreateReview(){
        // Submit a review
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setRating(4);
        reviewDTO.setReviewComment("Great experience");
        reviewDTO.setRestaurantId(1L);
        reviewDTO.setUserId(2L);

        RestaurantDao mockedRestaurant = new RestaurantDao();
        mockedRestaurant.setId(1L);

        UserDao mockedUser = new UserDao();
        mockedUser.setUserId(2L);

        reviewService.createReview();
    }

    @Test
    public void testGetAllRestaurantReviews(){
        // Get all reviews of a restaurant
    }

    @Test
    public void testGetAllUserReviews(){
        // Get all reviews of a user
    }
}
