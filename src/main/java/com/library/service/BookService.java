package com.library.service;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.dto.BookDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing books in the library system.
 * Handles book creation, updates, deletion, and retrieval operations.
 * Maintains relationships between books and authors.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    /**
     * Creates a new book with an existing author.
     * Each book must have an author and contains title, genre, and price information.
     *
     * @param bookDTO the book information to create
     * @return the created book as DTO
     * @throws ResourceNotFoundException if the specified author does not exist
     */
    public BookDTO createBook(BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setGenre(bookDTO.getGenre());
        book.setPrice(bookDTO.getPrice());
        book.setAuthor(author);

        return convertToDTO(bookRepository.save(book));
    }


    /**
     * Retrieves a book by its ID.
     *
     * @param id the book ID to retrieve
     * @return the book information as DTO
     * @throws ResourceNotFoundException if the book is not found
     */
    public BookDTO getBook(Long id) {
        return convertToDTO(findBookById(id));
    }

    /**
     * Retrieves all books in the system.
     *
     * @return list of all books as DTOs
     */
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing book's information.
     * Allows modification of title, genre, price, and author.
     *
     * @param id the book ID to update
     * @param bookDTO the new book information
     * @return the updated book as DTO
     * @throws ResourceNotFoundException if the book or new author is not found
     */
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = findBookById(id);
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        book.setTitle(bookDTO.getTitle());
        book.setGenre(bookDTO.getGenre());
        book.setPrice(bookDTO.getPrice());
        book.setAuthor(author);

        return convertToDTO(bookRepository.save(book));
    }

    /**
     * Deletes a book from the system.
     *
     * @param id the book ID to delete
     * @throws ResourceNotFoundException if the book is not found
     */
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    /**
     * Helper method to find a book by ID.
     *
     * @param id the book ID to find
     * @return the found book entity
     * @throws ResourceNotFoundException if the book is not found
     */
    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    /**
     * Converts a Book entity to BookDTO.
     * Includes book details and author ID.
     *
     * @param book the book entity to convert
     * @return the book as DTO
     */
    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setGenre(book.getGenre());
        dto.setPrice(book.getPrice());
        dto.setAuthorId(book.getAuthor().getId());
        return dto;
    }
}
