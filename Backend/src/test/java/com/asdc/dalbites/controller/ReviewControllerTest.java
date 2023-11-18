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
}