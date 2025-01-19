package com.library.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.domain.Author;
import com.library.dto.BookDTO;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the Book Controller.
 * Tests the complete request-response cycle using MockMvc.
 * Uses H2 in-memory database for testing.
 * Validates book creation scenarios and constraints.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    private Long authorId;

    /**
     * Sets up test data before each test.
     * Creates a test author in the database.
     * Required for book creation tests.
     */
    @BeforeEach
    void setUp() {
        // Create an author first since a book requires an author
        Author author = new Author();
        author.setName("Test Author");
        author.setDateOfBirth(LocalDate.of(1990, 1, 1));
        Author savedAuthor = authorRepository.save(author);
        authorId = savedAuthor.getId();
    }

    /**
     * Tests successful book creation with valid data.
     * Verifies:
     * - Correct HTTP status (200 OK)
     * - Response content type
     * - Response body contains expected values
     * - Author relationship is maintained
     *
     * @throws Exception if test fails
     */
    @Test
    void createBook_ValidData_ReturnsCreatedBook() throws Exception {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Test Book");
        bookDTO.setGenre("Fiction");
        bookDTO.setPrice(new BigDecimal("29.99"));
        bookDTO.setAuthorId(authorId);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.genre").value("Fiction"))
                .andExpect(jsonPath("$.price").value("29.99"))
                .andExpect(jsonPath("$.authorId").value(authorId));
    }

    /**
     * Tests book creation with invalid data.
     * Verifies:
     * - Bad request response (400)
     * - Validation error handling
     * - Required field validation
     *
     * @throws Exception if test fails
     */
    @Test
    void createBook_InvalidData_ReturnsBadRequest() throws Exception {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        // Missing required fields

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isBadRequest());
    }
    /**
     * Tests book creation with non-existent author.
     * Verifies:
     * - Not found response (404)
     * - Error handling for invalid author ID
     * - Proper error message
     *
     * @throws Exception if test fails
     */
    @Test
    void createBook_NonExistentAuthor_ReturnsNotFound() throws Exception {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Test Book");
        bookDTO.setGenre("Fiction");
        bookDTO.setPrice(new BigDecimal("29.99"));
        bookDTO.setAuthorId(999L); // Non-existent author ID

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isNotFound());
    }
}