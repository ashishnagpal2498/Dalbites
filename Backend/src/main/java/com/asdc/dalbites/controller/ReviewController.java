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

        return null;
    }

    @GetMapping("/{restaurantId}")
    public List<ReviewDTO> getRestaurantReviews(@PathVariable Long restaurantId){
        return null;
    }

    @GetMapping("/{restaurantId}/user")
    public ResponseEntity<ReviewDTO> getRestaurantReviewByUser(@RequestHeader("Authorization") String bearerToken, @PathVariable Long restaurantId) {
        return null;
    }

    @PutMapping
    public ResponseEntity<ReviewDTO> updateRestaurantReviewByUser(@RequestBody ReviewDTO updatedReviewDTO) {
        return null;
    }
}