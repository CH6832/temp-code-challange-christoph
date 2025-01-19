package com.library.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * Entity representing a book loan in the library system.
 * Manages the relationship between members and books during the lending process.
 * Tracks loan dates without time components as per business requirements.
 * Part of the system that enforces the 5-book limit per member rule.
 */
@Entity
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private LocalDate lendDate;

    private LocalDate returnDate;
}
