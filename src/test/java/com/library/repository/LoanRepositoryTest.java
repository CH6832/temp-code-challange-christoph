package com.library.repository;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Repository layer tests for Loan entity.
 * Tests loan-related database operations and queries.
 * Uses H2 in-memory database with test profile.
 * Validates loan management business rules.
 */
@DataJpaTest
@ActiveProfiles("test")
class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Member member;
    private Book book;

    /**
     * Sets up test data before each test.
     * Creates necessary entities in the following order:
     * 1. Author (required for Book)
     * 2. Book (required for Loan)
     * 3. Member (required for Loan)
     *
     * This setup enables testing of loan operations with valid entities.
     */
    @BeforeEach
    void setUp() {
        // Create Author
        Author author = new Author();
        author.setName("Test Author");
        author.setDateOfBirth(LocalDate.of(1990, 1, 1));
        author = authorRepository.save(author);

        // Create Book
        book = new Book();
        book.setTitle("Test Book");
        book.setGenre("Fiction");
        book.setPrice(new java.math.BigDecimal("29.99"));
        book.setAuthor(author);
        book = bookRepository.save(book);

        // Create Member
        member = new Member();
        member.setUsername("testuser");
        member.setEmail("test@test.com");
        member.setAddress("Test Address");
        member.setPhoneNumber("1234567890");
        member = memberRepository.save(member);
    }

    /**
     * Tests counting active loans for a member.
     * Verifies the query that enforces the 5-book limit rule.
     *
     * Test steps:
     * 1. Create a loan for the test member
     * 2. Count active loans
     * 3. Verify count matches expected value
     */
    @Test
    void countByMemberIdAndReturnDateIsNull_ReturnsCorrectCount() {
        // Arrange
        Loan loan = new Loan();
        loan.setMember(member);
        loan.setBook(book);
        loan.setLendDate(LocalDate.now());
        loanRepository.save(loan);

        // Act
        long count = loanRepository.countByMemberIdAndReturnDateIsNull(member.getId());

        // Assert
        assertEquals(1, count);
    }

    /**
     * Tests checking if a book is currently loaned out.
     * Verifies the query that prevents multiple loans of the same book.
     *
     * Test steps:
     * 1. Create an active loan for a book
     * 2. Check if book is marked as loaned
     * 3. Verify query returns true for active loan
     */
    @Test
    void existsByBookIdAndReturnDateIsNull_ReturnsTrueForActiveLoan() {
        // Arrange
        Loan loan = new Loan();
        loan.setMember(member);
        loan.setBook(book);
        loan.setLendDate(LocalDate.now());
        loanRepository.save(loan);

        // Act & Assert
        assertEquals(true, loanRepository.existsByBookIdAndReturnDateIsNull(book.getId()));
    }
}