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
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.persistence.Tuple;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
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
        //repository.save(new Review("review1", "author1", "review_source1", 5, "title1", "product_name1",new Date() ));
        //repository.findAll().forEach(i -> System.out.println(getReviewDetails(i)));
        return repository.findAll();
    }

    public List<Review> findAll(MultiValueMap<String, String> filters) {
        System.out.println("Fetch all records...");
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
        //TODO  List<User> findByAuthorAndReviewSource(String author, String reviewSource);
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

    public String getReviewDetails(Review review) {
        System.out.println(
                "Review: " + review.getReview() + ", author: " + review.getAuthor()
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
