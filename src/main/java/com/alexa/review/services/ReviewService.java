package com.alexa.review.services;

import com.alexa.review.entities.Review;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface ReviewService {

    /**
     * Get list of all Reviews
     *
     * @return a list of all reviews
     */
    public List<Review> findAll();

    /**
     * @param com.alexa.review.entities.Review Get list of all Reviews
     * @return review
     */
    public Review createOrUpdate(Review review);
}
