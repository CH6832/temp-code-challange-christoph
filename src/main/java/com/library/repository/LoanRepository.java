package com.library.repository;

import com.library.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for {@link Loan} entity.
 * Provides CRUD operations and custom queries for loan management.
 * Handles active loans tracking and loan limit enforcement.
 * Extends {@link JpaRepository} to inherit standard data access operations.
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {

    /**
     * Counts the number of active (unreturned) loans for a specific member.
     * Used to enforce the maximum loan limit of 5 books per member.
     *
     * @param memberId the ID of the member to check
     * @return the number of active loans for the member
     */
    long countByMemberIdAndReturnDateIsNull(Long memberId);

    /**
     * Checks if a specific book is currently on loan (not returned).
     * Used to prevent multiple loans of the same book.
     *
     * @param bookId the ID of the book to check
     * @return true if the book is currently loaned out, false otherwise
     */
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);

    /**
     * Retrieves all active loans for a specific member.
     * Active loans are those where the return date is null.
     *
     * @param memberId the ID of the member
     * @return list of active loans for the member
     */
    List<Loan> findByMemberIdAndReturnDateIsNull(Long memberId);
}
