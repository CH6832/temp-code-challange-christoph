package com.library.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for Book entities.
 * Used for creating and updating book information through the API.
 * Enforces validation rules and maintains the relationship with authors.
 * Each book must have a unique title per author (enforced at database level).
 */
@Data
public class BookDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Author ID is required")
    private Long authorId;
}
