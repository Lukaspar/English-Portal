package com.lukaspar.ep.core.controller;

import com.lukaspar.ep.core.dto.ApiError;
import com.lukaspar.ep.core.exception.EnglishDictionaryAlreadyExists;
import com.lukaspar.ep.core.exception.EnglishDictionaryIsEmptyException;
import com.lukaspar.ep.core.exception.EnglishDictionaryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(EnglishDictionaryNotFoundException.class)
    protected ResponseEntity<ApiError> handleNotFoundException(Exception ex, WebRequest request){
        return handle(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(EnglishDictionaryIsEmptyException.class)
    protected ResponseEntity<ApiError> handleUnProcessableEntityException(Exception ex, WebRequest request){
        return handle(HttpStatus.UNPROCESSABLE_ENTITY, ex);
    }

    @ExceptionHandler(EnglishDictionaryAlreadyExists.class)
    protected ResponseEntity<ApiError> handleConflictException(Exception ex, WebRequest request){
        return handle(HttpStatus.CONFLICT, ex);
    }

    private ResponseEntity<ApiError> handle(HttpStatus status, Exception ex) {
        ApiError responseError = createErrorResponse(status, ex);
        log.error("Error during request {} {}, cause: {}", responseError.getHttpMethod(), responseError.getEndpoint(), responseError.getMessage());
        return ResponseEntity.status(status).body(responseError);
    }

    private ApiError createErrorResponse(HttpStatus status, Exception ex) {
        ApiError response = new ApiError();
        response.setMessage(ex.getMessage());
        response.setStatus(status);
        response.setEndpoint(request.getRequestURI());
        response.setHttpMethod(request.getMethod());
        return response;
    }
}
