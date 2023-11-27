package com.asdc.dalbites.mappers;

import com.asdc.dalbites.model.DAO.ReviewDao;
import com.asdc.dalbites.model.DTO.ReviewDTO;
import org.springframework.stereotype.Component;

/**
 * Class responsible for mapping between ReviewDao and ReviewDTO.
 */
@Component
public class ReviewMapper {

    /**
     * Converts ReviewDao to ReviewDTO.
     *
     * @param reviewDao The ReviewDao object to be converted.
     * @return The corresponding ReviewDTO object.
     */
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

    /**
     * Converts ReviewDTO to ReviewDao.
     *
     * @param reviewDTO The ReviewDTO object to be converted.
     * @return The corresponding ReviewDao object.
     */
    public ReviewDao toReviewEntity(ReviewDTO reviewDTO) {
        ReviewDao reviewDao = new ReviewDao();
        reviewDao.setRating(reviewDTO.getRating());
        reviewDao.setReviewComment(reviewDTO.getReviewComment());
        return reviewDao;
    }
}

