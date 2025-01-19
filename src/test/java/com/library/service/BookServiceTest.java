package com.library.service;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.dto.AuthorDTO;
import com.library.dto.BookDTO;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the BookService class.
 * Tests business logic for book management operations.
 * Uses Mockito for mocking dependencies.
 * Validates service layer operations in isolation.
 */
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    /**
     * Tests successful book creation with valid data.
     * Verifies:
     * - Author existence check
     * - Book creation with correct data
     * - Proper DTO conversion
     * - Return value validation
     *
     * Test steps:
     * 1. Prepare test data (BookDTO and Author)
     * 2. Configure mock behaviors
     * 3. Call service method
     * 4. Verify results
     */
    @Test
    void createBook_ValidData_Success() {
        // Arrange
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Test Book");
        bookDTO.setGenre("Fiction");
        bookDTO.setPrice(new BigDecimal("29.99"));
        bookDTO.setAuthorId(1L);

        Author author = new Author();
        author.setId(1L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        BookDTO result = bookService.createBook(bookDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        assertEquals("Fiction", result.getGenre());
    }
}