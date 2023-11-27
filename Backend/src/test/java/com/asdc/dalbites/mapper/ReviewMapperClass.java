package com.asdc.dalbites.mapper;

import com.asdc.dalbites.mappers.ReviewMapper;
import com.asdc.dalbites.model.DAO.ReviewDao;
import com.asdc.dalbites.model.DTO.ReviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ReviewMapperTest {

    @Autowired
    private ReviewMapper reviewMapper;

    @Test
    void toReviewEntity() {
        ReviewDTO reviewDTO = createSampleReviewDTO();
        ReviewDao reviewDao = reviewMapper.toReviewEntity(reviewDTO);
        assertEquals(reviewDTO.getRating(), reviewDao.getRating());
        assertEquals(reviewDTO.getReviewComment(), reviewDao.getReviewComment());
    }

    private ReviewDao createSampleReviewDao() {
        ReviewDao reviewDao = new ReviewDao();
        reviewDao.setReviewComment("Great place!");
        return reviewDao;
    }

    private ReviewDTO createSampleReviewDTO() {
        ReviewDTO reviewDTO = new ReviewDTO();;
        reviewDTO.setReviewComment("Great place!");
        return reviewDTO;
    }
}
