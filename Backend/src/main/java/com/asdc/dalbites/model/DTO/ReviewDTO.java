package com.asdc.dalbites.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    public ReviewDTO(long reviewId, long restaurantId, int rating, String comment, long userId) {
        this.reviewId = reviewId;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.reviewComment = comment;
        this.userId = userId;
    }
    public ReviewDTO(){

    }
}

