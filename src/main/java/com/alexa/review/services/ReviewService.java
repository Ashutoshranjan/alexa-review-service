package com.alexa.review.services;

import com.alexa.review.entities.Review;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;

@Component
public interface ReviewService {

    /**
     * Get list of all Reviews
     *
     * @return a list of all reviews
     * @param filters
     */
    public List<Review> findAll(MultiValueMap<String, String> filters);

//    public List<Review> findAll(final Date startDate, Date endDate);

    /**
     * @param com.alexa.review.entities.Review Get list of all Reviews
     * @return review
     */
    public Review createOrUpdate(Review review);
}
