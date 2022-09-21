package com.alexa.review.enums;

public enum Ratting {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final Integer value;
    private Ratting(Integer value) {
        this.value=value;
    }
}
