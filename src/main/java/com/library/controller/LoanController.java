package com.library.controller;

import com.library.dto.LoanDTO;
import com.library.exception.BusinessException;
import com.library.exception.ResourceNotFoundException;
import com.library.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing book loans.
 * Provides endpoints for loan creation, retrieval, and book returns.
 * All endpoints are under the "/api/loans" base path.
 * Implements the 5-book limit per member business rule.
 */
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@Tag(name = "Loan Controller", description = "Loan management endpoints")
public class LoanController {
    private final LoanService loanService;

    /**
     * Creates a new loan for a book to a member.
     * Validates the loan request against business rules:
     * - Member cannot exceed 5 active loans
     * - Book must be available (not currently loaned)
     * - Both member and book must exist
     *
     * @param loanDTO the loan information containing member and book IDs
     * @return ResponseEntity containing the created loan
     * @throws BusinessException if loan limit exceeded or book unavailable
     * @throws ResourceNotFoundException if member or book not found
     */
    @PostMapping
    @Operation(summary = "Create a new loan")
    public ResponseEntity<LoanDTO> createLoan(@Valid @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.createLoan(loanDTO));
    }

    /**
     * Retrieves a specific loan by its ID.
     *
     * @param id the ID of the loan to retrieve
     * @return ResponseEntity containing the loan information
     * @throws ResourceNotFoundException if loan is not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a loan by ID")
    public ResponseEntity<LoanDTO> getLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoan(id));
    }

    /**
     * Retrieves all loans in the system.
     * Returns both active and completed loans.
     *
     * @return ResponseEntity containing list of all loans
     */
    @GetMapping
    @Operation(summary = "Get all loans")
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    /**
     * Processes the return of a borrowed book.
     * Updates the loan record with return date.
     * Makes the book available for new loans.
     *
     * @param id the ID of the loan to process return
     * @return ResponseEntity containing the updated loan information
     * @throws ResourceNotFoundException if loan is not found
     * @throws BusinessException if book was already returned
     */
    @PutMapping("/{id}/return")
    @Operation(summary = "Return a book")
    public ResponseEntity<LoanDTO> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnBook(id));
    }
}
