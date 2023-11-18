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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private RestaurantRepository restaurantRepository;

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

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setRating(4);
        reviewDTO.setReviewComment("Great experience");
        reviewDTO.setRestaurantId(1L);
        reviewDTO.setUserId(2L);

        RestaurantDao mockedRestaurant = new RestaurantDao();
        mockedRestaurant.setId(1L);

        UserDao mockedUser = new UserDao();
        mockedUser.setUserId(2L);

        ReviewDao mockedReviewDao = new ReviewDao();
        mockedReviewDao.setRating(reviewDTO.getRating());
        mockedReviewDao.setReviewComment(reviewDTO.getReviewComment());
        mockedReviewDao.setRestaurant(mockedRestaurant);
        mockedReviewDao.setUser(mockedUser);

        // Mocking repository methods
        Mockito.when(restaurantRepository.findByLogin_Id(reviewDTO.getRestaurantId())).thenReturn(mockedRestaurant);
        Mockito.when(userRepository.findByLogin_Id(reviewDTO.getUserId())).thenReturn(mockedUser);
        Mockito.when(reviewMapper.toReviewEntity(reviewDTO)).thenReturn(mockedReviewDao);
        Mockito.when(reviewRepository.save(Mockito.any(ReviewDao.class))).thenReturn(mockedReviewDao);
        Mockito.when(reviewMapper.toReviewDTO(mockedReviewDao)).thenReturn(reviewDTO);

        // Act
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);

        // Assert
        assertEquals(reviewDTO, createdReview);
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
