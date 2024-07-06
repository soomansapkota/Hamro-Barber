package com.example.hamro_barber.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<?> customException(CustomException exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", exception.getLocalizedMessage());
        error.put("status", "failed");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> badRequestException(BadRequestException exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", exception.getLocalizedMessage());
        error.put("status", "failed");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
//
//    @ExceptionHandler({UnAuthrizedAccessException.class})
//    public ResponseEntity<?> unAuthorizedAccess(UnAuthrizedAccessException exception) {
//        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
//
//    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", exception.getLocalizedMessage());
        error.put("status", "failed");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
