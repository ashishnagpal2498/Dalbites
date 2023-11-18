package com.asdc.dalbites.controller;

import com.asdc.dalbites.model.DTO.ReviewDTO;
import com.asdc.dalbites.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserReviews() {
        String bearerToken = "dummyToken-Value";
        List<ReviewDTO> reviewDTOs = Arrays.asList(
                new ReviewDTO(),
                new ReviewDTO()
        );

        when(reviewService.getAllUserReviews(bearerToken)).thenReturn(reviewDTOs);

    }
}