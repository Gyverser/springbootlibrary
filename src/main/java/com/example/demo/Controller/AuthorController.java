package com.example.demo.Controller;

import com.example.demo.Entity.Author;
import com.example.demo.Repository.AuthorRepository;
import com.example.demo.Service.AuthorService;
import com.example.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;
    private BookService bookService;

    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;

    }

    @GetMapping
    public String getAuthors(Model model) {

        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/authors/list";
    }

    @GetMapping("/{authorId}/books")
    public String getBooksByAuthor(@PathVariable Long authorId, Model model) {
        model.addAttribute("author", authorService.getAuthorById(authorId));
        model.addAttribute("books", bookService.getBooksByAuthorId(authorId));
        return "books/authors/author-books";
    }

    @GetMapping("/addAuthor")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "books/authors/addAuthor";
    }

    @PostMapping("/addAuthor")
    public String addAuthor(@ModelAttribute("author") Author author) {
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }


}
