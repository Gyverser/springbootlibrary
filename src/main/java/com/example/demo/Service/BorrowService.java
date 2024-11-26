package com.example.demo.Service;

import com.example.demo.Entity.Book;
import com.example.demo.Entity.Member;
import com.example.demo.Repository.BookRepository;
import com.example.demo.Repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BorrowService(BookRepository bookRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void addBorrowedBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + memberId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));

        member.addBorrowedBook(book);
        book.getBorrowers().add(member);

        memberRepository.save(member);
        bookRepository.save(book);
    }

    @Transactional
    public void removeBorrowedBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + memberId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));

        member.removeBorrowedBook(book);
        book.getBorrowers().remove(member);

        memberRepository.save(member);
        bookRepository.save(book);
    }
}