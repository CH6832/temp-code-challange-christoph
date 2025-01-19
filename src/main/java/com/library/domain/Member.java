package com.library.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a library member.
 * Stores member information and tracks their loan history.
 * Members are uniquely identified by their username and email.
 * Each member can have up to 5 active loans at a time.
 */
@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "member")
    private List<Loan> loans = new ArrayList<>();
}
