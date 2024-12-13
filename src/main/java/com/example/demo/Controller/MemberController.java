package com.example.demo.Controller;

import com.example.demo.Entity.Book;
import com.example.demo.Entity.Member;
import com.example.demo.Service.BookService;
import com.example.demo.Service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;
    private BookService bookService;

    public MemberController(MemberService memberService, BookService bookService) {
        this.memberService = memberService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "books/members/list";
    }

    @GetMapping("/add")
    public String addNewMember(Model model) {
        model.addAttribute("member", new Member());
        return "books/members/add";
    }

    @PostMapping("/add")
    public String saveMember(@Valid @ModelAttribute("member") Member member, BindingResult result) {
        if (result.hasErrors()) {
            return "books/members/add";
        }
        memberService.saveMember(member);
        return "redirect:/members";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        return "books/members/Edit";
    }

    @PostMapping("/edit/{id}")
    public String saveMember(@PathVariable Long id, @Valid @ModelAttribute("member") Member member, BindingResult result) {
        if(result.hasErrors()) {
            return "books/members/Edit";
        }

        member.setId(id);
        memberService.saveMember(member);
        return "redirect:/members";
    }

    @GetMapping("/{id}/books")
    public String getMemberBooks(@PathVariable Long id, Model model) {
        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        model.addAttribute("borrowedBooks", member.getBorrowedBooks());
        return "books/members/member-books";
    }

    @GetMapping("/{id}/books/borrow")
    public String showBorrowPage(@PathVariable Long id, Model model) {
        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        return "books/members/borrow";
    }
}
