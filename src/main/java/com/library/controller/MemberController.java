package com.library.controller;

import com.library.dto.MemberDTO;
import com.library.exception.BusinessException;
import com.library.exception.ResourceNotFoundException;
import com.library.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing library members.
 * Provides CRUD operations for member management.
 * All endpoints are under the "/api/members" base path.
 * Implements input validation and proper error handling.
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "Member Controller", description = "Member management endpoints")
public class MemberController {
    private final MemberService memberService;

    /**
     * Creates a new member.
     * Validates member information and ensures uniqueness of username and email.
     *
     * @param memberDTO the member information to create
     * @return ResponseEntity containing the created member
     * @throws BusinessException if username or email already exists
     */
    @PostMapping
    @Operation(summary = "Create a new member")
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.createMember(memberDTO));
    }

    /**
     * Retrieves a specific member by their ID.
     *
     * @param id the ID of the member to retrieve
     * @return ResponseEntity containing the member information
     * @throws ResourceNotFoundException if member is not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a member by ID")
    public ResponseEntity<MemberDTO> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

    /**
     * Retrieves all members in the system.
     *
     * @return ResponseEntity containing list of all members
     */
    @GetMapping
    @Operation(summary = "Get all members")
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /**
     * Updates an existing member's information.
     * Validates updated information and ensures uniqueness constraints.
     *
     * @param id the ID of the member to update
     * @param memberDTO the new member information
     * @return ResponseEntity containing the updated member
     * @throws ResourceNotFoundException if member is not found
     * @throws BusinessException if updated username or email conflicts
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a member")
    public ResponseEntity<MemberDTO> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.updateMember(id, memberDTO));
    }

    /**
     * Deletes a member from the system.
     * Verifies that the member has no active loans before deletion.
     *
     * @param id the ID of the member to delete
     * @return ResponseEntity with no content on successful deletion
     * @throws ResourceNotFoundException if member is not found
     * @throws BusinessException if member has active loans
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a member")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
