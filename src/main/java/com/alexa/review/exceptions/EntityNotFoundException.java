package com.alexa.review.exceptions;

public class EntityNotFoundException extends Throwable {

    public EntityNotFoundException(String string) {
        super(string);
    }

    private static final long serialVersionUID = 1L;
}
