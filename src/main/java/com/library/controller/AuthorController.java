package com.library.controller;

import com.library.dto.AuthorDTO;
import com.library.exception.BusinessException;
import com.library.exception.ResourceNotFoundException;
import com.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing authors in the library system.
 * Provides CRUD operations for author management.
 * All endpoints are under the "/api/authors" base path.
 * Handles author-book relationships and validations.
 */
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Tag(name = "Author Controller", description = "Author management endpoints")
public class AuthorController {
    private final AuthorService authorService;

    /**
     * Creates a new author in the system.
     * Validates author information including date of birth.
     *
     * @param authorDTO the author information to create
     * @return ResponseEntity containing the created author
     * @throws BusinessException if validation fails
     */
    @PostMapping
    @Operation(summary = "Create a new author")
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.createAuthor(authorDTO));
    }

    /**
     * Retrieves a specific author by their ID.
     * Returns author's basic information without book details.
     *
     * @param id the ID of the author to retrieve
     * @return ResponseEntity containing the author information
     * @throws ResourceNotFoundException if author is not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get an author by ID")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    /**
     * Retrieves all authors in the system.
     * Returns basic author information for all authors.
     *
     * @return ResponseEntity containing list of all authors
     */
    @GetMapping
    @Operation(summary = "Get all authors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    /**
     * Updates an existing author's information.
     * Validates updated information including date of birth.
     *
     * @param id the ID of the author to update
     * @param authorDTO the new author information
     * @return ResponseEntity containing the updated author
     * @throws ResourceNotFoundException if author is not found
     * @throws BusinessException if validation fails
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an author")
    public ResponseEntity<AuthorDTO> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(id, authorDTO));
    }

    /**
     * Deletes an author from the system.
     * Verifies that the author has no associated books before deletion.
     *
     * @param id the ID of the author to delete
     * @return ResponseEntity with no content on successful deletion
     * @throws ResourceNotFoundException if author is not found
     * @throws BusinessException if author has associated books
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
