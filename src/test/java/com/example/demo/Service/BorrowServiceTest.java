package com.example.demo.Service;

import com.example.demo.Entity.Book;
import com.example.demo.Entity.Member;
import com.example.demo.Repository.BookRepository;
import com.example.demo.Repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BorrowServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private BorrowService borrowService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBorrowedBookSuccess() {
        Long memberId = 1L;
        Long bookId = 2L;

        Member mockMember = new Member("John Doe", "M32193219");
        mockMember.setId(memberId);

        Book mockBook = new Book("Spring Boot Guide", "123456789", 1);
        mockBook.setId(bookId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        borrowService.addBorrowedBook(memberId, bookId);

        verify(memberRepository, times(1)).findById(memberId);
        verify(bookRepository, times(1)).findById(bookId);
        verify(memberRepository, times(1)).save(mockMember);
    }

    @Test
    void testAddBorrowedBookMemberNotFound() {
        Long memberId = 1L;
        Long bookId = 2L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        try {
            borrowService.addBorrowedBook(memberId, bookId);
        } catch (EntityNotFoundException e) {
            assertEquals("Member not found with ID: " + memberId, e.getMessage());
        }

        verify(memberRepository, times(1)).findById(memberId);
        verify(bookRepository, never()).findById(bookId);
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void testAddBorrowedBookBookNotFound() {
        Long memberId = 1L;
        Long bookId = 2L;

        Member mockMember = new Member("John Doe", "M32321132");
        mockMember.setId(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        try {
            borrowService.addBorrowedBook(memberId, bookId);
        } catch (EntityNotFoundException e) {
            assertEquals("Book not found with ID: " + bookId, e.getMessage());
        }

        verify(memberRepository, times(1)).findById(memberId);
        verify(bookRepository, times(1)).findById(bookId);
        verify(memberRepository, never()).save(any(Member.class));
    }
}