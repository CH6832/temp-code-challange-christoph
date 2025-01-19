package com.library.exception;

/**
 * Exception thrown when business rules are violated in the library system.
 * This runtime exception handles business logic violations such as:
 * - Exceeding the maximum loan limit (5 books per member)
 * - Attempting to loan an already loaned book
 * - Attempting to return an already returned book
 * - Duplicate username/email in member registration
 *
 * This exception is typically caught by the global exception handler and
 * converted to an appropriate HTTP 400 Bad Request response.
 */
public class BusinessException extends RuntimeException {

    /**
     * Constructs a new BusinessException with the specified detail message.
     * The message should clearly describe which business rule was violated.
     *
     * @param message detailed message explaining the business rule violation
     *               (e.g., "Member has reached the maximum limit of 5 books",
     *                      "Book is already loaned",
     *                      "Username already exists")
     */
    public BusinessException(String message) {
        super(message);
    }
}