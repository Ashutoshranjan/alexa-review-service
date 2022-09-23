package com.alexa.review.controllers;

import com.alexa.review.dto.GenericResponse;
import com.alexa.review.entities.CountRating;
import com.alexa.review.entities.MonthlyAvgByReviewSource;
import com.alexa.review.entities.MonthlyAvgReviewSource;
import com.alexa.review.entities.Review;
import com.alexa.review.exceptions.EntityNotFoundException;
import com.alexa.review.services.impl.MyUserDetailsServiceImpl;
import com.alexa.review.services.impl.ReviewServiceImpl;
import com.alexa.review.utils.GenericResponseUtils;
import com.alexa.review.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/alexa")
public class ReviewController {

    @Autowired
    private ReviewServiceImpl service;

    @GetMapping("/reviews")
    public ResponseEntity<GenericResponse> getReviews(@RequestParam MultiValueMap<String, String> filters) throws EntityNotFoundException {
        //"start_date:2022-01-01,end_date:2022-02-02,review_source:iTunes,rating:5"
        List<Review> productReviews = service.findAll(filters);
        if (productReviews.isEmpty()) {
            return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseOK("No reviews found."));
        }
        return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseOK(productReviews));
    }

    @PutMapping("/reviews")
    @ApiOperation(value = "Create new review or update existing review if already present")
    public ResponseEntity<GenericResponse> addOrUpdateReview(@RequestBody @NonNull Review reviewDetails) throws EntityNotFoundException {
        //check for ratting 1to5
        //Uniqueness of a record is derived from author, review source and date.
        Review productReview = new Review();
        if ((reviewDetails.getRating() != 1 && reviewDetails.getRating() != 2 && reviewDetails.getRating() != 3 && reviewDetails.getRating() != 4 && reviewDetails.getRating() != 5) || reviewDetails.getAuthor().isEmpty() || reviewDetails.getReviewSource().isEmpty()) {
            return ResponseEntity.unprocessableEntity().body(GenericResponseUtils.buildGenericResponseError(reviewDetails));
        } else
            productReview = service.createOrUpdate(reviewDetails);
        return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseOK(productReview));
    }

    @GetMapping("/reviews/{rating}/total_count")
    public ResponseEntity<GenericResponse> getRatingCount(@PathVariable(name = "rating") final int rating) {
        if (rating == 1 || rating == 2 || rating == 3 || rating == 4 || rating == 5) {
            Long count = service.getTotalCount(rating);
            return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseOK(count));
        }
        return ResponseEntity.badRequest().body(GenericResponseUtils.buildGenericResponseError("Invalid rating value."));
    }

    @GetMapping("/reviews/ratings/total_count")
    public ResponseEntity<GenericResponse> getAllRatingCounts() {
        List<CountRating> countRating = service.getTotalRatingCounts();
        return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseOK(countRating));
    }

    @GetMapping("/reviews/ratings/monthly_avg")
    public ResponseEntity<GenericResponse> getAllRatingAverage() {
        List<MonthlyAvgReviewSource> monthlyAvgReviewSources = service.getAllMonthlyRatingAvg();
        return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseOK(monthlyAvgReviewSources));
    }

    @GetMapping("/reviews/ratings/monthly_avg/{review_source}")
    public ResponseEntity<GenericResponse> getRatingAverageByReviewSource(@PathVariable(name = "review_source") final String reviewSource) {
        List<MonthlyAvgByReviewSource> monthlyAvgByReviewSource = service.getMonthlyRatingAvgByReviewSource(reviewSource);
        return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseOK(monthlyAvgByReviewSource));
    }

    @GetMapping("/reviews/review_sources")
    public ResponseEntity<GenericResponse> getAllReviewSources() {
        List<String> reviewSources = service.getReviewSources();
        return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseOK(reviewSources));
    }
}
