package com.alexa.review.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="review_details")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String review;
    private String author;
    @Column(name = "review_source")
    private String reviewSource;
    private Integer rating;
    private String title;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "reviewed_date")
    private Date reviewedDate;

    public Date getReviewedDate() {
        return reviewedDate;
    }
}
