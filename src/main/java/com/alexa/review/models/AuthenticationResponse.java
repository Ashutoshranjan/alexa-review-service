package com.alexa.review.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse implements Serializable {
    private final String jwt;
}
