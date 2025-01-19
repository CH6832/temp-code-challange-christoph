package com.library.exception;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Standardized error response object for the API.
 * Used to provide consistent error information across all endpoints.
 * Contains status code, error message, and timestamp of the error occurrence.
 */
@Data
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    /**
     * Constructs a new ErrorResponse with the specified status and message.
     * Automatically sets the timestamp to the current time.
     *
     * @param status HTTP status code representing the error type
     * @param message detailed description of the error
     */
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}