package com.alexa.review.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {

    private String status;
    private Integer code;
    private List<String> messages;
    private Object data;
}
