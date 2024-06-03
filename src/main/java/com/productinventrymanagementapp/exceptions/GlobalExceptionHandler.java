package com.productinventrymanagementapp.exceptions;

import com.productinventrymanagementapp.constants.ApplicationConstants;
import com.productinventrymanagementapp.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleInvalidRequestException(InvalidRequestException ex) {
        return ResponseHandler.generateResponse(ApplicationConstants.BAD_REQUEST,HttpStatus.BAD_REQUEST,ApplicationConstants.INVALID_DATA_IN_PAYLOAD_ERROR_MESSAGE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder("400 Bad Request: ");
        return ResponseHandler.generateResponse(ApplicationConstants.BAD_REQUEST,HttpStatus.BAD_REQUEST,ApplicationConstants.INVALID_DATA_IN_PAYLOAD_ERROR_MESSAGE);
    }
}
