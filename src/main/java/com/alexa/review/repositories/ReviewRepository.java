package com.alexa.review.repositories;

import com.alexa.review.entities.Review;
import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    List<Review> findByReviewedDateBetweenOrderByReviewedDateDesc(Date startDate, Date endDate);

}
