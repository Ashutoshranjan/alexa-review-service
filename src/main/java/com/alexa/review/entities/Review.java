package com.alexa.review.entities;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Document("allreviewdetails")
public class Review {

    private String review;
    private String author;
    @Field("review_source")
    //@BsonProperty(value = "review_source")
    private String review_source;
    //private String reviewSource;
    private Integer rating;
    private String title;
    @Field("product_name")
    //@BsonProperty(value = "product_name")
    private String product_name;
    @Field("reviewed_date")
    //@BsonProperty(value = "reviewed_date")
    private Date reviewed_date;

}
