package com.demo.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extract validation error details from the exception
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // Create a custom error response
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);

        // Return the error response along with appropriate HTTP status
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Define additional exception handlers as needed...
}
