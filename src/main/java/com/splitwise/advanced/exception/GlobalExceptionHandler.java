package com.splitwise.advanced.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        CustomErrorResponse customError = new CustomErrorResponse(404, e.getMessage(), new Date(System.currentTimeMillis()));
        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }


}
