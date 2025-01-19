package com.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for Loan entities.
 * Used for creating and managing book loans through the API.
 * Handles the relationship between members and books for lending operations.
 * Tracks loan dates without time components as per business requirements.
 */
@Data
public class LoanDTO {
    private Long id;

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    private LocalDate lendDate;
    private LocalDate returnDate;
}
