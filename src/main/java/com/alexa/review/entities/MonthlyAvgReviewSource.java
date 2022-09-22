package com.alexa.review.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class MonthlyAvgReviewSource {
    private String reviewedDate;
    private String reviewSource;
    private Double average;
}
