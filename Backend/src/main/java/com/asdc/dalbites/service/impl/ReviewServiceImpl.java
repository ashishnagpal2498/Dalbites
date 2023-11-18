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
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ReviewDTO createReview(String token, ReviewDTO reviewDTO){
        RestaurantDao restaurant = restaurantRepository.findById(reviewDTO.getRestaurantId()).orElse(null);
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(7));
        Long userId = Long.parseLong(tokenClaims.get("user_id").toString());
        UserDao user = userRepository.findByUserId(userId);

        ReviewDao reviewDao = reviewMapper.toReviewEntity(reviewDTO);

        reviewDao.setRestaurant(restaurant);
        reviewDao.setUser(user);
        ReviewDao createdReview = reviewRepository.save(reviewDao);
        return reviewMapper.toReviewDTO(createdReview);
    }

    @Override
    public List<ReviewDTO> getAllRestaurantReviews(Long restuarantId){
        List<ReviewDao> reviewDaos = reviewRepository.findByRestaurantId(restuarantId);
        return reviewDaos.stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getAllUserReviews(String token){
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(7));
        Long userId = Long.parseLong(tokenClaims.get("user_id").toString());
        List<ReviewDao> reviewDaos = reviewRepository.findByUser_UserId(userId);
        return reviewDaos.stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }
}
