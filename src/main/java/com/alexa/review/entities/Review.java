package com.alexa.review.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
//@Document("allreviewdetails")
@Entity
@Table(name="review_details")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String review;
    private String author;
    //@JsonIgnore
    @Column(name = "review_source")
    private String reviewSource;
    private Integer rating;
    private String title;
    //@JsonIgnore
    @Column(name = "product_name")
    private String productName;
    //@JsonIgnore
    @Column(name = "reviewed_date")
    private Date reviewedDate;

    public Date getReviewedDate() {
        return reviewedDate;
    }
/*
    @JsonIgnore()
    public void setReviewedDate(Date reviewedDate) {
        this.reviewedDate = reviewedDate;
    }
    @JsonIgnore()
    public String getReviewSource() {
        return reviewSource;
    }
    @JsonIgnore()
    public void setReviewSource(String reviewSource) {
        this.reviewSource = reviewSource;
    }
    @JsonIgnore()
    public String getProductName() {
        return productName;
    }
    @JsonIgnore()
    public void setProductName(String productName) {
        this.productName = productName;
    }*/
}
