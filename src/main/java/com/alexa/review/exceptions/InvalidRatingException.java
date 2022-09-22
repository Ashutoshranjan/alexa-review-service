package com.alexa.review.exceptions;

public class InvalidRatingException extends Throwable {

    public InvalidRatingException(String string) {
        super(string);
    }

    private static final long serialVersionUID = 1L;
}
