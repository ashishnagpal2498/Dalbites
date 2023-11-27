package com.asdc.dalbites.controller;

import com.asdc.dalbites.model.DTO.ReviewDTO;
import com.asdc.dalbites.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling review-related operations.
 * This class provides endpoints for retrieving user reviews,
 * creating a new review, retrieving restaurant reviews, retrieving a
 * restaurant review by a user, and updating a restaurant review by a user.
 */
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * Retrieves reviews submitted by the authenticated user.
     *
     * @param bearerToken The authentication token in the request header.
     * @return ResponseEntity with a list of user reviews or an empty list.
     */
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getUserReviews(@RequestHeader("Authorization") String bearerToken) {
        List<ReviewDTO> reviews = reviewService.getAllUserReviews(bearerToken);
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }

    /**
     * Creates a new review for a restaurant.
     *
     * @param bearerToken The authentication token in the request header.
     * @param reviewDTO    The details of the review to be created.
     * @return ResponseEntity with the created review details.
     */
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestHeader("Authorization") String bearerToken, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(bearerToken,reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    /**
     * Retrieves reviews for a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant to retrieve reviews for.
     * @return List of reviews for the specified restaurant.
     */
    @GetMapping("/{restaurantId}")
    public List<ReviewDTO> getRestaurantReviews(@PathVariable Long restaurantId){
        return reviewService.getAllRestaurantReviews(restaurantId);
    }

    /**
     * Retrieves the review submitted by the authenticated user for a specific restaurant.
     *
     * @param bearerToken The authentication token in the request header.
     * @param restaurantId The ID of the restaurant to retrieve the review for.
     * @return ResponseEntity with the review details or a not found status.
     */

    @GetMapping("/{restaurantId}/user")
    public ResponseEntity<ReviewDTO> getRestaurantReviewByUser(@RequestHeader("Authorization") String bearerToken, @PathVariable Long restaurantId) {
        ReviewDTO createdReview = reviewService.getRestaurantReviewByUser(bearerToken,restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(createdReview);
    }

    /**
     * Updates the review submitted by the authenticated user for a restaurant.
     *
     * @param updatedReviewDTO The updated details of the review.
     * @return ResponseEntity with the updated review details or an error status.
     */
    @PutMapping
    public ResponseEntity<ReviewDTO> updateRestaurantReviewByUser(@RequestBody ReviewDTO updatedReviewDTO) {
        ReviewDTO createdReview = reviewService.updateRestaurantReviewByUser(updatedReviewDTO);
        return ResponseEntity.status(HttpStatus.OK).body(createdReview);
    }
}