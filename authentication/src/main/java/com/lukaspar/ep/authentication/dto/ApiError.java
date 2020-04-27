package com.lukaspar.ep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private String message;
    private String httpMethod;
    private String endpoint;
    private HttpStatus status;
}
