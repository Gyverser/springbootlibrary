package com.example.demo.Service;

import com.example.demo.Entity.Member;
import com.example.demo.Repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMembers() {
        List<Member> mockMembers = Arrays.asList(
                new Member("John Doe", "M312321321"),
                new Member("Jane Smith", "M123123")
        );

        when(memberRepository.findAll()).thenReturn(mockMembers);

        List<Member> members = memberService.getAllMembers();

        assertNotNull(members);
        assertEquals(2, members.size());
        assertEquals("John Doe", members.get(0).getName());
        assertEquals("M312321321", members.get(0).getMembershipNumber());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void testGetMemberById() {
        Long id = 1L;
        Member mockMember = new Member("John Doe", "M321231");
        mockMember.setId(id);

        when(memberRepository.findById(id)).thenReturn(Optional.of(mockMember));

        Member member = memberService.getMemberById(id);

        assertNotNull(member);
        assertEquals("John Doe", member.getName());
        assertEquals("M321231", member.getMembershipNumber());
        assertEquals(id, member.getId());
        verify(memberRepository, times(1)).findById(id);
    }

    @Test
    void testSaveMember() {
        Member memberToSave = new Member("John Doe", "M3123123");
        Member savedMember = new Member("John Doe", "M3123123");
        savedMember.setId(1L);

        when(memberRepository.save(memberToSave)).thenReturn(savedMember);

        Member result = memberService.saveMember(memberToSave);

        assertNotNull(result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("M3123123", result.getMembershipNumber());
        verify(memberRepository, times(1)).save(memberToSave);
    }

    @Test
    void testDeleteMember() {
        Long id = 1L;

        memberService.deleteMember(id);

        verify(memberRepository, times(1)).deleteById(id);
    }
}