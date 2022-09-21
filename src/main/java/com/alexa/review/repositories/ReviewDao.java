package com.alexa.review.repositories;

import com.alexa.review.entities.Review;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@NoArgsConstructor
@Repository
public class ReviewDao {
    EntityManager entityManager;
    List<Review> findReviewsByAuthorAndReviewSource(String author,String reviewSource){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);

        Root<Review> reviewRoot = criteriaQuery.from(Review.class);
        Predicate authorPredicate = criteriaBuilder.equal(reviewRoot.get("author"), author);
        Predicate reviewSourcePredicate = criteriaBuilder.equal(reviewRoot.get("reviewSource"), reviewSource);
        criteriaQuery.where(authorPredicate, reviewSourcePredicate);

        TypedQuery<Review> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
