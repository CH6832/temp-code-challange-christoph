package com.library.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

/**
 * Entity representing a book in the library system.
 * Implements the single copy per book requirement through a unique constraint on title and author.
 * Uses optimistic locking for concurrent modifications.
 * Maintains a many-to-one relationship with Author entity.
 */
@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_book_title_author",
                columnNames = {"title", "author_id"}
        )
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Version
    private Long version;
}
