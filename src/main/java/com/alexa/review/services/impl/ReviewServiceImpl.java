package com.alexa.review.services.impl;

import com.alexa.review.entities.CountRating;
import com.alexa.review.entities.MonthlyAvgByReviewSource;
import com.alexa.review.entities.MonthlyAvgReviewSource;
import com.alexa.review.entities.Review;
import com.alexa.review.repositories.ReviewRepository;
import com.alexa.review.services.ReviewService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository repository;

    public List<Review> findAll() {
        System.out.println("Fetch all records...");
        return repository.findAll();
    }

    public List<Review> findAll(MultiValueMap<String, String> filters) {
        System.out.println("Fetch all records based on filters...");
        //"start_date:2022-01-01,end_date:2022-02-02,review_source:iTunes,rating:5"
        List<Review> reviewList = new ArrayList<>();
        String startDateValue = "", endDateValue = "", reviewSource = "", rating = "";
        if (!filters.isEmpty()) {
            String[] str = filters.get("filter").get(0).replaceAll("\"", "").split(",");
            for (String s : str) {
                String[] tmp = s.split(":");
                switch (tmp[0]) {
                    case "start_date":
                        startDateValue = tmp[1];
                        break;
                    case "end_date":
                        endDateValue = tmp[1];
                        break;
                    case "review_source":
                        reviewSource = tmp[1];
                        break;
                    case "rating":
                        rating = tmp[1];
                        break;
                }
            }

            //if start date is present
            if (!startDateValue.isEmpty()) {
                try {
                    Date endDate = endDateValue.isEmpty() ? new Date() : new SimpleDateFormat("yyyy-MM-dd").parse(endDateValue);
                    reviewList = repository.findByReviewedDateBetweenOrderByReviewedDateDesc(new SimpleDateFormat("yyyy-MM-dd").parse(startDateValue), endDate);
                } catch (Exception ex) {
                    //date is not in correct format
                }
            } else
                reviewList = repository.findAll();
            //if review_source is present
            if (!reviewSource.isEmpty()) {
                String finalReviewSource = reviewSource;
                reviewList = reviewList.stream().filter(i -> i.getReviewSource().equals(finalReviewSource)).collect(Collectors.toList());
            }
            //if rating is present
            if (!rating.isEmpty()) {
                String finalRating = rating;
                reviewList = reviewList.stream().filter(i -> i.getRating() == Integer.valueOf(finalRating)).collect(Collectors.toList());
            }

            return reviewList;

        } else
            return repository.findAll();


    }

    public Review createOrUpdate(Review review) {
        List<Review> reviewList = repository.findAll(where(hasAuthor(review.getAuthor())).and(hasReviewSource(review.getReviewSource())));
        if (reviewList.isEmpty()) {
            //new entry
            repository.save(review);
        } else {
            //update entry
            Review reviewObj = reviewList.get(0);
            if (!review.getReview().isEmpty())
                reviewObj.setReview(review.getReview());
            reviewObj.setRating(review.getRating());
            if (!review.getTitle().isEmpty())
                reviewObj.setTitle(review.getTitle());
            if (!review.getProductName().isEmpty())
                reviewObj.setProductName(review.getProductName());
            try {
                if (null != review.getReviewedDate())
                    reviewObj.setReviewedDate(review.getReviewedDate());
                else
                    reviewObj.setReviewedDate(new Date());
            } catch (Exception ex) {
                reviewObj.setReviewedDate(new Date());
            }

            repository.save(reviewObj);

        }
        return review;
    }

    public Long getTotalCount(int rating) {
        return repository.countByRating(rating);
    }

    @Override
    public List<CountRating> getTotalRatingCounts() {
        return repository.getCountByRating();
    }

    public List<MonthlyAvgReviewSource> getAllMonthlyRatingAvg() {
        return repository.getAvgByReviewSource();
    }

    public List<MonthlyAvgByReviewSource> getMonthlyRatingAvgByReviewSource(String reviewSource) {
        return repository.getAvgByReviewSource(reviewSource);
    }

    public List<String> getReviewSources() {
        return repository.findDistinctReviewSource();
    }

    static Specification<Review> hasAuthor(String author) {
        return (review, cq, cb) -> cb.equal(review.get("author"), author);
    }

    static Specification<Review> hasReviewSource(String reviewSource) {
        return (review, cq, cb) -> cb.equal(review.get("reviewSource"), reviewSource);
    }
}
