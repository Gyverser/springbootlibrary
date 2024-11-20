package com.example.demo.Controller;

import com.example.demo.Entity.Book;
import com.example.demo.Entity.Member;
import com.example.demo.Service.BookService;
import com.example.demo.Service.BorrowService;
import com.example.demo.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class BorrowController {

    private final MemberService memberService;
    private final BookService bookService;
    private final BorrowService borrowService;

    @Autowired
    public BorrowController(MemberService memberService, BookService bookService, BorrowService borrowService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.borrowService = borrowService;
    }

    @GetMapping("/borrow")
    public String showBorrowPage(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("books", bookService.getAllBooks());

        return "/books/members/borrow";
    }

    @PostMapping("/borrow")
    public String borrowABook(@RequestParam Long memberId, @RequestParam Long bookId) {
        System.out.println("Member ID: " + memberId);
        System.out.println("Book ID: " + bookId);
        borrowService.addBorrowedBook(memberId, bookId);
        return "redirect:/members";
    }

}
