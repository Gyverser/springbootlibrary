package com.example.demo.Controller;

import com.example.demo.Entity.Author;
import com.example.demo.Entity.Book;
import com.example.demo.Entity.Member;
import com.example.demo.Service.AuthorService;
import com.example.demo.Service.BookService;
import com.example.demo.Service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookService bookService;
    private AuthorService authorService;
    private MemberService memberService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, MemberService memberService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.memberService = memberService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/list";
    }

    @GetMapping("/create")
    public String createBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("members", memberService.getAllMembers());
        return "books/create";
    }

    @PostMapping("/create")
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult result, @RequestParam Long authorId, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            return "books/create";
        }
        Author author = authorService.getAuthorById(authorId);
        if (author == null) {
            result.rejectValue("author", "error.book", "Invalid author selected");
            model.addAttribute("authors", authorService.getAllAuthors());
            return "books/create";
        }
        book.setAuthor(author);
        bookService.saveBook(book);
        return "redirect:/books";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/edit";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@Valid @ModelAttribute("book") Book book, BindingResult result, @RequestParam Long authorId, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            return "books/edit";
        }

        Author author = authorService.getAuthorById(authorId);
        book.setAuthor(author);
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
