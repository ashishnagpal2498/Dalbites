package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.mappers.ReviewMapper;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DAO.ReviewDao;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.model.DTO.ReviewDTO;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.repository.ReviewRepository;
import com.asdc.dalbites.repository.UserRepository;
import com.asdc.dalbites.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private JwtTokenUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void testCreateReview(){
        // Submit a review
        String token = "yourToken";
        ReviewDTO reviewDTO = new ReviewDTO(1L, 1L, 5, "Great!", 123L);
        Claims claims = new DefaultClaims();
        claims.put("user_id", 123L);

        // Mocking repository calls
        when(jwtUtil.getAllClaimsFromToken(anyString())).thenReturn(claims);
        when(userRepository.findByUserId(anyLong())).thenReturn(new UserDao());
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(new RestaurantDao()));

        ReviewDao reviewDao = new ReviewDao();
        reviewDao.setRating(reviewDTO.getRating());
        reviewDao.setReviewComment(reviewDTO.getReviewComment());
        reviewDao.setUser(new UserDao());
        reviewDao.setRestaurant(new RestaurantDao());
        reviewDao.setCreatedAt(new Date());

        when(reviewMapper.toReviewEntity(any())).thenReturn(reviewDao);
        when(reviewRepository.save(any())).thenReturn(reviewDao);
        when(reviewMapper.toReviewDTO(any())).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.createReview(token, reviewDTO);

        // Assert the result
        assertNotNull(result);
        assertEquals(reviewDTO.getRating(), result.getRating());
        assertEquals(reviewDTO.getReviewComment(), result.getReviewComment());
        assertEquals(reviewDTO.getUserId(), result.getUserId());
        assertEquals(reviewDTO.getRestaurantId(), result.getRestaurantId());
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
