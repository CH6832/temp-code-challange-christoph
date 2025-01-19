package com.library.controller;

import com.library.dto.BookDTO;
import com.library.exception.BusinessException;
import com.library.exception.ResourceNotFoundException;
import com.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing books in the library system.
 * Provides CRUD operations for book management.
 * All endpoints are under the "/api/books" base path.
 * Enforces the single copy per book constraint.
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "Book management endpoints")
public class BookController {
    private final BookService bookService;

    /**
     * Creates a new book in the system.
     * Validates book information and ensures author exists.
     * Enforces unique title per author constraint.
     *
     * @param bookDTO the book information to create
     * @return ResponseEntity containing the created book
     * @throws ResourceNotFoundException if author not found
     * @throws BusinessException if book title already exists for author
     */
    @PostMapping
    @Operation(summary = "Create a new book")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

    /**
     * Retrieves a specific book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return ResponseEntity containing the book information
     * @throws ResourceNotFoundException if book is not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    /**
     * Retrieves all books in the system.
     * Returns books with their associated author information.
     *
     * @return ResponseEntity containing list of all books
     */
    @GetMapping
    @Operation(summary = "Get all books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    /**
     * Updates an existing book's information.
     * Validates updated information and ensures author exists.
     * Maintains unique title per author constraint.
     *
     * @param id the ID of the book to update
     * @param bookDTO the new book information
     * @return ResponseEntity containing the updated book
     * @throws ResourceNotFoundException if book or author not found
     * @throws BusinessException if updated title conflicts with existing book
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a book")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    /**
     * Deletes a book from the system.
     * Verifies that the book is not currently loaned out.
     *
     * @param id the ID of the book to delete
     * @return ResponseEntity with no content on successful deletion
     * @throws ResourceNotFoundException if book not found
     * @throws BusinessException if book is currently loaned out
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}