package com.asdc.dalbites.mappers;

import com.asdc.dalbites.model.DAO.ReviewDao;
import com.asdc.dalbites.model.DTO.ReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toReviewDTO(ReviewDao reviewDao) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setRating(reviewDao.getRating());
        reviewDTO.setReviewComment(reviewDao.getReviewComment());
        reviewDTO.setUserId(reviewDao.getUser().getUserId());
        reviewDTO.setRestaurantId(reviewDao.getRestaurant().getId());
        reviewDTO.setReviewId(reviewDao.getReviewId());
        reviewDTO.setUserName(reviewDao.getUser().getName());
        reviewDTO.setRestaurantName(reviewDao.getRestaurant().getName());
        reviewDTO.setCreatedAt(reviewDao.getCreatedAt());
        return reviewDTO;
    }

    public ReviewDao toReviewEntity(ReviewDTO reviewDTO) {
        ReviewDao reviewDao = new ReviewDao();
        reviewDao.setRating(reviewDTO.getRating());
        reviewDao.setReviewComment(reviewDTO.getReviewComment());
        return reviewDao;
    }
}

