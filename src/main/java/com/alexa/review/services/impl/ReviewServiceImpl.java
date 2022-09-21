package com.alexa.review.services.impl;

import com.alexa.review.entities.Review;
import com.alexa.review.repositories.ReviewRepository;
import com.alexa.review.services.ReviewService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

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
        //repository.save(new Review("review1", "author1", "review_source1", 5, "title1", "product_name1",new Date() ));
        //repository.findAll().forEach(i -> System.out.println(getReviewDetails(i)));
        return repository.findAll();
    }

    /*@Override
    public Review createOrUpdate(String review, String author, String reviewSource, Integer rating, String title, String productName, Date reviewedDate) {
        //Boolean isAuthorExists = repository.findBy()
        List<Review> ll = repository.findAll(where(hasAuthor(author)).and(hasReviewSource(reviewSource)));
        if (ll.isEmpty()) {
            //new entry
            Review reviewObj = new Review();
            reviewObj.setReview(review);
            reviewObj.setAuthor(author);
            reviewObj.setReviewSource(reviewSource);
            reviewObj.setRating(rating);
            reviewObj.setTitle(title);
            reviewObj.setProductName(productName);
            reviewObj.setReviewedDate(reviewedDate);
            repository.save(reviewObj);
            return reviewObj;
        } else {
            //update entry
            Review reviewObj = ll.get(0);
            if (!review.isEmpty())
                reviewObj.setReview(review);
            reviewObj.setRating(rating);
            if (!title.isEmpty())
                reviewObj.setTitle(title);
            if (!productName.isEmpty())
                reviewObj.setProductName(productName);
            try {
                if (null != reviewedDate)
                    reviewObj.setReviewedDate(reviewedDate);
            } catch (Exception ex) {
                reviewObj.setReviewedDate(new Date());
            }

            repository.save(reviewObj);
            return reviewObj;
        }
    }
       */

    //Review reviewRow = repository.findAll().stream().filter(i -> i.getAuthor().equals(review.getAuthor()) && i.getReviewSource().equals(review.getReviewSource())).findFirst().orElse(null);
       /* List<Review> ll = repository.findByAuthor(review.getAuthor());
        if(ll.isEmpty()){
            //new record
            repository.save(review);
        }
        else{
        Review reviewRow = repository.findByAuthor(review.getAuthor()).stream().filter(i -> i.getReviewSource().equals(review.getReviewSource())).findFirst().orElse(null);
            if(null == reviewRow){
                //new record
                repository.save(review);
            }
            else {
                reviewRow.setReview(review.getReview());
                reviewRow.setReviewedDate(review.getReviewedDate());
                reviewRow.setRating(review.getRating());
                reviewRow.setProductName(review.getProductName());
                reviewRow.setTitle(review.getTitle());
                repository.save(reviewRow);
            }
        }
       return review;
    }*/

    public Review createOrUpdate(Review review) {
        //Boolean isAuthorExists = repository.findBy()
        List<Review> ll = repository.findAll(where(hasAuthor(review.getAuthor())).and(hasReviewSource(review.getReviewSource())));
        if (ll.isEmpty()) {
            //new entry
            repository.save(review);
        } else {
            //update entry
            Review reviewObj = ll.get(0);
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
    public String getReviewDetails(Review review){
        System.out.println(
                "Review: " + review.getReview() + ", author: " +review.getAuthor()
                        + ", review_source: " + review.getReviewSource()
        );
        return "";
    }

    static Specification<Review> hasAuthor(String author) {
        return (review, cq, cb) -> cb.equal(review.get("author"), author);
    }

    static Specification<Review> hasReviewSource(String reviewSource) {
        return (review, cq, cb) -> cb.equal(review.get("reviewSource"), reviewSource);
    }
}
