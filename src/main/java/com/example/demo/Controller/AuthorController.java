package com.example.demo.Controller;

import com.example.demo.Entity.Author;
import com.example.demo.Repository.AuthorRepository;
import com.example.demo.Service.AuthorService;
import com.example.demo.Service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/add")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "books/authors/add";
    }

    @PostMapping("/add")
    public String addAuthor(@Valid @ModelAttribute("author") Author author, BindingResult result) {
        if(result.hasErrors()) {
            return "books/authors/add";
        }

        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        authorService.deleteAuthor(author.getId());
        return "redirect:/authors";
    }
}
