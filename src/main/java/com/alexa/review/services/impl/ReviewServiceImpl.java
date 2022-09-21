package com.alexa.review.services.impl;

import com.alexa.review.entities.Review;
import com.alexa.review.repositories.ReviewRepository;
import com.alexa.review.services.ReviewService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository repository;

    public List<Review> findAll() {
        System.out.println("Data creation started...");
        //repository.save(new Review("review1", "author1", "review_source1", 5, "title1", "product_name1",new Date() ));
        repository.findAll().forEach(i -> System.out.println(getReviewDetails(i)));
        return repository.findAll();
    }

    @Override
    public Review createOrUpdate(Review review) {
        //Review reviewRow = repository.findAll().stream().filter(i -> i.getAuthor().equals(review.getAuthor()) && i.getReviewSource().equals(review.getReviewSource())).findFirst().orElse(null);
        List<Review> ll = repository.findByAuthor(review.getAuthor());
        if(ll.isEmpty()){
            //new record
            repository.save(review);
        }
        else{
        Review reviewRow = repository.findByAuthor(review.getAuthor()).stream().filter(i -> i.getReview_source().equals(review.getReview_source())).findFirst().orElse(null);
            if(null == reviewRow){
                //new record
                repository.save(review);
            }
            else {
                reviewRow.setReview(review.getReview());
                reviewRow.setReviewed_date(review.getReviewed_date());
                reviewRow.setRating(review.getRating());
                reviewRow.setProduct_name(review.getProduct_name());
                reviewRow.setTitle(review.getTitle());
                repository.save(reviewRow);
            }
        }
        return review;
    }

    public String getReviewDetails(Review review){
        System.out.println(
                "Review: " + review.getReview() + ", author: " +review.getAuthor()
                        + ", review_source: " + review.getReview_source()
        );
        return "";
    }
}
