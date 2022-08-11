package com.gromyk.carworkshops.rest;

import com.gromyk.carworkshops.domain.exceptions.TimeConflictException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({TimeConflictException.class})
    protected ResponseEntity<Object> handleTimeConflict(TimeConflictException exception, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalParameter(IllegalArgumentException exception, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleGeneralException(Exception exception, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
