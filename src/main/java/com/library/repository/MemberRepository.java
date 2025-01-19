package com.library.repository;

import com.library.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Member} entity.
 * Provides CRUD operations and custom queries for member management.
 * Extends {@link JpaRepository} to inherit standard data access operations.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Checks if a member with the given username exists.
     * Used for enforcing unique username constraint during member registration and updates.
     *
     * @param username the username to check
     * @return true if a member with the username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a member with the given email exists.
     * Used for enforcing unique email constraint during member registration and updates.
     *
     * @param email the email address to check
     * @return true if a member with the email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
