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
        return null;
    }
}