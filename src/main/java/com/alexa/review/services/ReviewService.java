package com.alexa.review.services;

import com.alexa.review.entities.CountRating;
import com.alexa.review.entities.MonthlyAvgByReviewSource;
import com.alexa.review.entities.MonthlyAvgReviewSource;
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
     * @param filters
     * @return a list of all reviews
     */
    public List<Review> findAll(MultiValueMap<String, String> filters);

    public Review createOrUpdate(Review review);

    public Long getTotalCount(int rating);

    public List<CountRating> getTotalRatingCounts();

    public List<MonthlyAvgReviewSource> getAllMonthlyRatingAvg();

    public List<MonthlyAvgByReviewSource> getMonthlyRatingAvgByReviewSource(String reviewSource);
}
