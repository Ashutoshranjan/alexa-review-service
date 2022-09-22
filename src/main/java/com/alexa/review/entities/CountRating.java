package com.alexa.review.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CountRating {
    private int rating;
    private long count;
}
