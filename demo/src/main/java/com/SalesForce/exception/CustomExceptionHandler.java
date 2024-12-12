/*
 * Copyright (c) ICANIO
 */

package com.SalesForce.exception;
import com.SalesForce.model.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    // Exception handler for CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        // Generate and return an error response using the provided CustomException
        return getErrorResponse(ex.getErrorCode(), ex.getMessage(), ex.getHttpStatus(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        // Generate and return an error response using the provided CustomException
        return getErrorResponse(500, "Something went wrong, please try again", HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // Private method to generate a generic error response
    private ResponseEntity<?> getErrorResponse(int code, String message, HttpStatus httpStatusCode, String errorMessage) {
        // Create a GenericResponse object with success status based on the HTTP status code
        GenericResponse genericResponse = new GenericResponse(httpStatusCode.is2xxSuccessful(), code, message, errorMessage);

        // Return the GenericResponse wrapped in a ResponseEntity with the specified HTTP status code
        return new ResponseEntity<>(genericResponse, httpStatusCode);
    }
}
