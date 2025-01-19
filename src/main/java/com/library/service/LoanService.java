package com.library.service;

import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.Member;
import com.library.dto.LoanDTO;
import com.library.exception.BusinessException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing book loans in the library system.
 * Handles loan creation, book returns, and enforces lending rules such as:
 * - Maximum 5 books per member
 * - Single copy per book
 * - Proper loan date tracking
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LoanService {
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    /**
     * Creates a new loan for a book to a member.
     * Enforces business rules:
     * - Member cannot exceed 5 active loans
     * - Book must be available (not currently loaned)
     * - Both member and book must exist
     *
     * @param loanDTO the loan information containing member and book IDs
     * @return the created loan as DTO
     * @throws ResourceNotFoundException if member or book not found
     * @throws BusinessException if business rules are violated (book unavailable or loan limit reached)
     */
    public LoanDTO createLoan(LoanDTO loanDTO) {
        Member member = memberRepository.findById(loanDTO.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        // Add validation for book availability
        if (loanRepository.existsByBookIdAndReturnDateIsNull(loanDTO.getBookId())) {
            throw new BusinessException("Book is already loaned");
        }

        // Check if member has reached the loan limit
        if (loanRepository.countByMemberIdAndReturnDateIsNull(member.getId()) >= 5) {
            throw new BusinessException("Member has reached the maximum limit of 5 books");
        }

        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        // Check if book is already loaned
        if (loanRepository.existsByBookIdAndReturnDateIsNull(book.getId())) {
            throw new BusinessException("Book is already loaned");
        }

        Loan loan = new Loan();
        loan.setMember(member);
        loan.setBook(book);
        loan.setLendDate(LocalDate.now());

        return convertToDTO(loanRepository.save(loan));
    }

    /**
     * Processes the return of a loaned book.
     * Sets the return date to the current date.
     *
     * @param loanId the ID of the loan to process
     * @return the updated loan information as DTO
     * @throws ResourceNotFoundException if loan not found
     * @throws BusinessException if book was already returned
     */
    public LoanDTO returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        if (loan.getReturnDate() != null) {
            throw new BusinessException("Book already returned");
        }

        loan.setReturnDate(LocalDate.now());
        return convertToDTO(loanRepository.save(loan));
    }

    /**
     * Retrieves a specific loan by its ID.
     *
     * @param id the loan ID to retrieve
     * @return the loan information as DTO
     * @throws ResourceNotFoundException if loan not found
     */
    public LoanDTO getLoan(Long id) {
        return convertToDTO(findLoanById(id));
    }

    /**
     * Retrieves all loans in the system.
     *
     * @return list of all loans as DTOs
     */
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to find a loan by ID.
     *
     * @param id the loan ID to find
     * @return the found loan entity
     * @throws ResourceNotFoundException if loan not found
     */
    private Loan findLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
    }

    /**
     * Converts a Loan entity to LoanDTO.
     * Includes member and book IDs, and loan dates.
     *
     * @param loan the loan entity to convert
     * @return the loan as DTO
     */
    private LoanDTO convertToDTO(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setId(loan.getId());
        dto.setMemberId(loan.getMember().getId());
        dto.setBookId(loan.getBook().getId());
        dto.setLendDate(loan.getLendDate());
        dto.setReturnDate(loan.getReturnDate());
        return dto;
    }
}
