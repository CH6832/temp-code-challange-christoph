package com.library.repository;

import com.library.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Author} entity.
 * Provides standard CRUD operations for author management.
 * Extends {@link JpaRepository} to inherit basic data access operations including:
 * - save
 * - findById
 * - findAll
 * - delete
 * - count
 *
 * No custom queries are needed as basic JPA operations cover all required functionality:
 * - Author creation and updates through save()
 * - Author retrieval through findById() and findAll()
 * - Author deletion through delete()
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
}