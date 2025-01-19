package com.library.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the library management system.
 * Provides centralized exception handling across all controllers.
 * Converts exceptions to appropriate HTTP responses with standardized error messages.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException.
     * Occurs when requested entities (books, authors, members, loans) are not found.
     *
     * @param ex the ResourceNotFoundException
     * @return ResponseEntity with 404 status and error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles BusinessException.
     * Occurs when business rules are violated (e.g., loan limits, duplicate entries).
     *
     * @param ex the BusinessException
     * @return ResponseEntity with 400 status and error details
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation exceptions.
     * Occurs when request payload fails validation constraints.
     * Returns the first validation error message.
     *
     * @param ex the MethodArgumentNotValidException
     * @return ResponseEntity with 400 status and validation error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles database integrity violations.
     * Occurs when database constraints are violated (e.g., unique constraints).
     * Provides specific message for book title uniqueness violation.
     *
     * @param ex the DataIntegrityViolationException
     * @return ResponseEntity with 409 status and constraint violation details
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = ex.getMessage().contains("uk_book_title_author")
                ? "A book with this title already exists for this author"
                : "Database constraint violation";

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                message
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}