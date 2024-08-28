package com.aman.chat_application.Exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<ErrorResponse> handleEmptyInput(EmptyInputException ex, WebRequest request) {
        String errorMessage = "Input field is Empty, Please look into it";
        logger.error(errorMessage, ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                errorMessage,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        String errorMessage = "No value present in DB, please change your request";
        logger.error(errorMessage, ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "No Data Found",
                errorMessage,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex, WebRequest request) {
        logger.error("Role not found: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Role Not Found",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        logger.error("Constraint violation: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Constraint Violation",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));

        logger.error("Validation error: {}", errorMessage, ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                errorMessage.toString(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        String errorMessage = "Invalid request body";
        logger.error(errorMessage, ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                errorMessage,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ConfigDataResourceNotFoundException ex, WebRequest request) {
        logger.error("Resource not found: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex, WebRequest request) {
        logger.error("Conflict occurred: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        logger.error("Illegal argument: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Illegal Argument",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
