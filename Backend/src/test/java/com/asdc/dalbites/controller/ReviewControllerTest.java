package com.asdc.dalbites.controller;

import com.asdc.dalbites.model.DTO.ReviewDTO;
import com.asdc.dalbites.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserReviews() {

        String bearerToken = "dummyJWT-Token";
        List<ReviewDTO> reviewDTOs = Arrays.asList(
                new ReviewDTO(),
                new ReviewDTO()
        );

        when(reviewService.getAllUserReviews(bearerToken)).thenReturn(reviewDTOs);

        ResponseEntity<List<ReviewDTO>> result = reviewController.getUserReviews(bearerToken);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(reviewDTOs, result.getBody());
    }

    @Test
    void testCreateReview() {
        String bearerToken = "JWTUserToken";
        ReviewDTO reviewDTO = new ReviewDTO();
        ReviewDTO createdReviewDTO = new ReviewDTO();

        when(reviewService.createReview(bearerToken, reviewDTO)).thenReturn(createdReviewDTO);

        ResponseEntity<ReviewDTO> result = reviewController.createReview(bearerToken, reviewDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(createdReviewDTO, result.getBody());
    }

    @Test
    void testGetRestaurantReviews() {
        Long restaurantId = 1L;
        List<ReviewDTO> reviewDTOs = Arrays.asList(
                new ReviewDTO(),
                new ReviewDTO()
        );

        when(reviewService.getAllRestaurantReviews(restaurantId)).thenReturn(reviewDTOs);

        List<ReviewDTO> result = reviewController.getRestaurantReviews(restaurantId);

        assertNotNull(result);
        assertEquals(reviewDTOs, result);
    }

    @Test
    void testGetRestaurantReviewByUser() {
        String bearerToken = "Token";
        Long restaurantId = 1L;
        ReviewDTO reviewDTO = new ReviewDTO();

        when(reviewService.getRestaurantReviewByUser(bearerToken, restaurantId)).thenReturn(reviewDTO);

        ResponseEntity<ReviewDTO> result = reviewController.getRestaurantReviewByUser(bearerToken, restaurantId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(reviewDTO, result.getBody());
    }

    @Test
    void testUpdateRestaurantReviewByUser() {
        ReviewDTO updatedReviewDTO = new ReviewDTO();
        ReviewDTO updatedReviewResultDTO = new ReviewDTO();

        when(reviewService.updateRestaurantReviewByUser(updatedReviewDTO)).thenReturn(updatedReviewResultDTO);

        ResponseEntity<ReviewDTO> result = reviewController.updateRestaurantReviewByUser(updatedReviewDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedReviewResultDTO, result.getBody());
    }
}