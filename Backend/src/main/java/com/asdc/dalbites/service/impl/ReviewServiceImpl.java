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
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO){
        RestaurantDao restaurant = restaurantRepository.findByLogin_Id(reviewDTO.getRestaurantId());
        UserDao user = userRepository.findByLogin_Id(reviewDTO.getUserId());

        ReviewDao reviewDao = reviewMapper.toReviewEntity(reviewDTO);

        reviewDao.setRestaurant(restaurant);
        reviewDao.setUser(user);
        ReviewDao createdReview = reviewRepository.save(reviewDao);
        return reviewMapper.toReviewDTO(createdReview);
    }

    @Override
    public void getAllRestaurantReviews(){

    }

    @Override
    public void getAllUserReviews(){

    }
}
