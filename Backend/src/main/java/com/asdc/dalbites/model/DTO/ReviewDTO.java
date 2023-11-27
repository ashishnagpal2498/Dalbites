package com.asdc.dalbites.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Data Transfer Object (DTO) for representing reviews.
 */
@Getter
@Setter
public class ReviewDTO {

    private Long reviewId;
    private Long restaurantId;
    private int rating;
    private String reviewComment;
    private Long userId;
    private String userName;
    private String restaurantName;
    private Date createdAt;

    /**
     * Constructor for creating a new ReviewDTO.
     *
     * @param reviewId      The unique identifier of the review.
     * @param restaurantId  The unique identifier of the restaurant associated with the review.
     * @param rating        The rating given in the review.
     * @param comment       The comment provided in the review.
     * @param userId        The unique identifier of the user who created the review.
     */
    public ReviewDTO(long reviewId, long restaurantId, int rating, String comment, long userId) {
        this.reviewId = reviewId;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.reviewComment = comment;
        this.userId = userId;
    }
    /**
     * Default constructor for ReviewDTO.
     */
    public ReviewDTO(){

    }
}

