package com.library.service;

import com.library.domain.Book;
import com.library.domain.Member;
import com.library.dto.LoanDTO;
import com.library.exception.BusinessException;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the LoanService class.
 * Tests business rules and constraints for loan operations.
 * Uses Mockito for mocking dependencies.
 * Focuses on loan creation validation scenarios.
 */
@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanService loanService;

    private LoanDTO loanDTO;
    private Member member;
    private Book book;

    /**
     * Sets up test data before each test.
     * Initializes:
     * - Test member with ID and username
     * - Test book with ID and details
     * - Loan DTO with member and book references
     */
    @BeforeEach
    void setUp() {
        // Setup member
        member = new Member();
        member.setId(1L);
        member.setUsername("testUser");

        // Setup book
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("29.99"));

        // Setup loan DTO
        loanDTO = new LoanDTO();
        loanDTO.setMemberId(1L);
        loanDTO.setBookId(1L);
    }

    /**
     * Tests loan creation when member has reached maximum loan limit.
     * Verifies that attempting to create a sixth loan throws BusinessException.
     *
     * Test scenario:
     * 1. Member exists
     * 2. Member already has 5 active loans
     * 3. Attempt to create new loan
     * 4. Expect BusinessException
     */
    @Test
    void createLoan_WhenMemberHasFiveBooks_ThrowsException() {
        // Arrange
        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));
        when(loanRepository.countByMemberIdAndReturnDateIsNull(1L))
                .thenReturn(5L);

        // Act & Assert
        assertThrows(BusinessException.class, () ->
                loanService.createLoan(loanDTO)
        );
    }

    /**
     * Tests loan creation when book is already loaned out.
     * Verifies that attempting to loan an unavailable book throws BusinessException.
     *
     * Test scenario:
     * 1. Member exists and has available loan slots
     * 2. Book exists but is already loaned
     * 3. Attempt to create loan
     * 4. Expect BusinessException
     */
    @Test
    void createLoan_WhenBookAlreadyLoaned_ThrowsException() {
        // Arrange
        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));
        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));
        when(loanRepository.countByMemberIdAndReturnDateIsNull(1L))
                .thenReturn(0L);
        when(loanRepository.existsByBookIdAndReturnDateIsNull(1L))
                .thenReturn(true);

        // Act & Assert
        assertThrows(BusinessException.class, () ->
                loanService.createLoan(loanDTO)
        );
    }
}