package com.library.service;

import com.library.domain.Member;
import com.library.dto.MemberDTO;
import com.library.exception.BusinessException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing library members.
 * Handles member creation, retrieval, updates, and deletion while enforcing business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * Creates a new member with unique username and email.
     *
     * @param memberDTO the member information to create
     * @return the created member as DTO
     * @throws BusinessException if username or email already exists
     */
    public MemberDTO createMember(MemberDTO memberDTO) {
        if (memberRepository.existsByUsername(memberDTO.getUsername())) {
            throw new BusinessException("Username already exists");
        }
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new BusinessException("Email already exists");
        }

        Member member = new Member();
        member.setUsername(memberDTO.getUsername());
        member.setEmail(memberDTO.getEmail());
        member.setAddress(memberDTO.getAddress());
        member.setPhoneNumber(memberDTO.getPhoneNumber());

        return convertToDTO(memberRepository.save(member));
    }

    /**
     * Retrieves a member by their ID.
     *
     * @param id the member ID
     * @return the member as DTO
     * @throws ResourceNotFoundException if member not found
     */
    public MemberDTO getMember(Long id) {
        return convertToDTO(findMemberById(id));
    }

    /**
     * Retrieves all members in the system.
     *
     * @return list of all members as DTOs
     */
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing member's information.
     *
     * @param id the member ID to update
     * @param memberDTO the new member information
     * @return the updated member as DTO
     * @throws ResourceNotFoundException if member not found
     * @throws BusinessException if new username or email conflicts with existing members
     */
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        Member member = findMemberById(id);

        // Check if new username/email conflicts with other members
        if (!member.getUsername().equals(memberDTO.getUsername()) &&
                memberRepository.existsByUsername(memberDTO.getUsername())) {
            throw new BusinessException("Username already exists");
        }
        if (!member.getEmail().equals(memberDTO.getEmail()) &&
                memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new BusinessException("Email already exists");
        }

        member.setUsername(memberDTO.getUsername());
        member.setEmail(memberDTO.getEmail());
        member.setAddress(memberDTO.getAddress());
        member.setPhoneNumber(memberDTO.getPhoneNumber());

        return convertToDTO(memberRepository.save(member));
    }

    /**
     * Deletes a member from the system.
     *
     * @param id the member ID to delete
     * @throws ResourceNotFoundException if member not found
     */
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }

    /**
     * Helper method to find a member by ID.
     *
     * @param id the member ID to find
     * @return the found member entity
     * @throws ResourceNotFoundException if member not found
     */
    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    /**
     * Converts a Member entity to MemberDTO.
     *
     * @param member the member entity to convert
     * @return the member as DTO
     */
    private MemberDTO convertToDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setUsername(member.getUsername());
        dto.setEmail(member.getEmail());
        dto.setAddress(member.getAddress());
        dto.setPhoneNumber(member.getPhoneNumber());
        return dto;
    }
}