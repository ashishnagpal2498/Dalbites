package com.asdc.dalbites.controller;

import com.asdc.dalbites.model.DTO.ReviewDTO;
import com.asdc.dalbites.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getUserReviews(@RequestHeader("Authorization") String bearerToken) {
        List<ReviewDTO> reviews = reviewService.getAllUserReviews(bearerToken);
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestHeader("Authorization") String bearerToken, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(bearerToken,reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @GetMapping("/{restaurantId}")
    public List<ReviewDTO> getRestaurantReviews(@PathVariable Long restaurantId){
        return reviewService.getAllRestaurantReviews(restaurantId);
    }

    @GetMapping("/{restaurantId}/user")
    public ResponseEntity<ReviewDTO> getRestaurantReviewByUser(@RequestHeader("Authorization") String bearerToken, @PathVariable Long restaurantId) {
        ReviewDTO createdReview = reviewService.getRestaurantReviewByUser(bearerToken,restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(createdReview);
    }

    @PutMapping
    public ResponseEntity<ReviewDTO> updateRestaurantReviewByUser(@RequestBody ReviewDTO updatedReviewDTO) {
        ReviewDTO createdReview = reviewService.updateRestaurantReviewByUser(updatedReviewDTO);
        return ResponseEntity.status(HttpStatus.OK).body(createdReview);
    }
}