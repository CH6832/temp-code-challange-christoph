package com.library.exception;

/**
 * Exception thrown when a requested resource cannot be found in the system.
 * This is a runtime exception used for handling missing entities such as:
 * - Books that don't exist
 * - Authors that don't exist
 * - Members that don't exist
 * - Loans that don't exist
 *
 * This exception is typically caught by the global exception handler and
 * converted to an appropriate HTTP 404 Not Found response.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     * The message should include the type and identifier of the resource that wasn't found.
     *
     * @param message detailed message explaining what resource was not found
     *               (e.g., "Book not found with id: 123")
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
