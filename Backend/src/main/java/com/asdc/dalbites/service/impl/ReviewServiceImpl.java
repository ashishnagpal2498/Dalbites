package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.mappers.ReviewMapper;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DAO.ReviewDao;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.model.DTO.ReviewDTO;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.repository.ReviewRepository;
import com.asdc.dalbites.repository.UserRepository;
import com.asdc.dalbites.service.ReviewService;
import com.asdc.dalbites.util.JwtTokenUtil;
import com.asdc.dalbites.util.Constants;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link ReviewService} interface providing methods for managing restaurant reviews.
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtUtil;

    /**
     * Creates a new review for a restaurant based on the provided information and user authentication token.
     *
     * @param token      The authentication token of the user creating the review.
     * @param reviewDTO  The data transfer object containing review information.
     * @return A {@link ReviewDTO} representing the newly created review.
     */
    @Override
    public ReviewDTO createReview(String token, ReviewDTO reviewDTO){
        RestaurantDao restaurant = restaurantRepository.findById(reviewDTO.getRestaurantId()).orElse(null);
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(Constants.TOKEN_START_INDEX));
        Long userId = Long.parseLong(tokenClaims.get("user_id").toString());
        UserDao user = userRepository.findByUserId(userId);

        ReviewDao reviewDao = reviewMapper.toReviewEntity(reviewDTO);

        reviewDao.setRestaurant(restaurant);
        reviewDao.setUser(user);
        ReviewDao createdReview = reviewRepository.save(reviewDao);
        return reviewMapper.toReviewDTO(createdReview);
    }

    /**
     * Retrieves all reviews for a specific restaurant.
     *
     * @param restuarantId  The ID of the restaurant for which reviews are retrieved.
     * @return A list of {@link ReviewDTO} representing the reviews for the restaurant.
     */
    @Override
    public List<ReviewDTO> getAllRestaurantReviews(Long restuarantId){
        List<ReviewDao> reviewDaos = reviewRepository.findByRestaurantId(restuarantId);
        return reviewDaos.stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all reviews submitted by a specific user.
     *
     * @param token  The authentication token of the user for whom reviews are retrieved.
     * @return A list of {@link ReviewDTO} representing the reviews submitted by the user.
     */
    @Override
    public List<ReviewDTO> getAllUserReviews(String token){
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(Constants.TOKEN_START_INDEX));
        Long userId = Long.parseLong(tokenClaims.get("user_id").toString());
        List<ReviewDao> reviewDaos = reviewRepository.findByUser_UserId(userId);
        return reviewDaos.stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a review submitted by a specific user for a particular restaurant.
     *
     * @param token         The authentication token of the user.
     * @param restaurantId  The ID of the restaurant for which the review is retrieved.
     * @return A {@link ReviewDTO} representing the user's review for the restaurant, or null if not found.
     */
    @Override
    public ReviewDTO getRestaurantReviewByUser(String token, Long restaurantId){
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(Constants.TOKEN_START_INDEX));
        Long userId = Long.parseLong(tokenClaims.get("user_id").toString());
        Optional<ReviewDao> reviewDao = reviewRepository.findByUser_UserIdAndRestaurant_Id(userId,restaurantId);
        return reviewDao.map(dao -> reviewMapper.toReviewDTO(dao)).orElse(null);
    }

    /**
     * Updates a review submitted by a user for a restaurant based on the provided information.
     *
     * @param updatedReviewDTO  The data transfer object containing updated review information.
     * @return A {@link ReviewDTO} representing the updated review, or null if the review is not found.
     */
    @Override
    public ReviewDTO updateRestaurantReviewByUser(ReviewDTO updatedReviewDTO) {
        Optional<ReviewDao> existingReviewOptional = reviewRepository.findById(updatedReviewDTO.getReviewId());

        if (existingReviewOptional.isPresent()) {
            ReviewDao existingReview = existingReviewOptional.get();
            existingReview.setRating(updatedReviewDTO.getRating());
            existingReview.setReviewComment(updatedReviewDTO.getReviewComment());

            ReviewDao updatedReview = reviewRepository.save(existingReview);

            return reviewMapper.toReviewDTO(updatedReview);
        } else return null;
    }
}
