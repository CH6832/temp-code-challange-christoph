package com.library.service;

import com.library.domain.Author;
import com.library.dto.AuthorDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing authors in the library system.
 * Handles author creation, updates, deletion, and retrieval operations.
 * Authors can have multiple books associated with them.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;

    /**
     * Creates a new author in the system.
     * Captures basic author information including name and date of birth.
     *
     * @param authorDTO the author information to create
     * @return the created author as DTO
     */
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setDateOfBirth(authorDTO.getDateOfBirth());

        return convertToDTO(authorRepository.save(author));
    }

    /**
     * Retrieves an author by their ID.
     *
     * @param id the author ID to retrieve
     * @return the author information as DTO
     * @throws ResourceNotFoundException if the author is not found
     */
    public AuthorDTO getAuthor(Long id) {
        return convertToDTO(findAuthorById(id));
    }

    /**
     * Retrieves all authors in the system.
     *
     * @return list of all authors as DTOs
     */
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing author's information.
     * Allows modification of name and date of birth.
     *
     * @param id the author ID to update
     * @param authorDTO the new author information
     * @return the updated author as DTO
     * @throws ResourceNotFoundException if the author is not found
     */
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = findAuthorById(id);
        author.setName(authorDTO.getName());
        author.setDateOfBirth(authorDTO.getDateOfBirth());

        return convertToDTO(authorRepository.save(author));
    }

    /**
     * Deletes an author from the system.
     * Note: This operation may be restricted if the author has associated books.
     *
     * @param id the author ID to delete
     * @throws ResourceNotFoundException if the author is not found
     */
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }

    /**
     * Helper method to find an author by ID.
     *
     * @param id the author ID to find
     * @return the found author entity
     * @throws ResourceNotFoundException if the author is not found
     */
    private Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    /**
     * Converts an Author entity to AuthorDTO.
     * Maps basic author information excluding book details.
     *
     * @param author the author entity to convert
     * @return the author as DTO
     */
    private AuthorDTO convertToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setDateOfBirth(author.getDateOfBirth());
        return dto;
    }
}