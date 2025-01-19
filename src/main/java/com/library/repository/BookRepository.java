package com.library.repository;

import com.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link Book} entity.
 * Provides CRUD operations and custom queries for book management.
 * Handles unique book constraints and author relationships.
 * Extends {@link JpaRepository} to inherit standard data access operations.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Checks if a book with the given title exists for a specific author.
     * Used to enforce the unique constraint of one copy per book per author.
     *
     * @param title the title of the book
     * @param authorId the ID of the author
     * @return true if a book with the same title exists for the author, false otherwise
     */
    boolean existsByTitleAndAuthorId(String title, Long authorId);

    /**
     * Finds a book by its title and author ID.
     * Useful for retrieving specific editions or verifying unique combinations.
     *
     * @param title the title of the book to find
     * @param authorId the ID of the book's author
     * @return Optional containing the book if found, empty Optional otherwise
     */
    Optional<Book> findByTitleAndAuthorId(String title, Long authorId);
}
