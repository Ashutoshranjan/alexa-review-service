package com.alexa.review.repositories;

import com.alexa.review.entities.CountRating;
import com.alexa.review.entities.MonthlyAvgByReviewSource;
import com.alexa.review.entities.MonthlyAvgReviewSource;
import com.alexa.review.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    List<Review> findByReviewedDateBetweenOrderByReviewedDateDesc(Date startDate, Date endDate);

    Long countByRating(int rating);

    @Query("select new com.alexa.review.entities.CountRating(r.rating, count(r.id)) from Review r group by r.rating order by r.rating")
    public List<CountRating> getCountByRating();

    @Query("select new com.alexa.review.entities.MonthlyAvgReviewSource(substring(r.reviewedDate,1,7), r.reviewSource, avg(r.rating)) from Review r group by substring(r.reviewedDate,1,7), r.reviewSource  order by substring(r.reviewedDate,1,7) desc, r.reviewSource asc")
    public List<MonthlyAvgReviewSource> getAvgByReviewSource();

    @Query("select new com.alexa.review.entities.MonthlyAvgByReviewSource(substring(r.reviewedDate,1,7), avg(r.rating)) from Review r where r.reviewSource = ?1 group by substring(r.reviewedDate,1,7)  order by substring(r.reviewedDate,1,7) desc")
    public List<MonthlyAvgByReviewSource> getAvgByReviewSource(String reviewSource);

    @Query("select distinct r.reviewSource from Review r")
    public List<String> findDistinctReviewSource();
}
