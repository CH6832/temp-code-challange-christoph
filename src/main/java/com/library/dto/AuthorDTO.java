package com.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for Author entities.
 * Used for creating and updating author information through the API.
 * Contains basic author information and validation constraints.
 * Authors can have multiple books associated with them.
 */
@Data
public class AuthorDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
}
