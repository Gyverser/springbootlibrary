package com.example.demo.Service;

import com.example.demo.Entity.Book;
import com.example.demo.Repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository theBookRepository) {
        bookRepository = theBookRepository;
    }

    @Cacheable(value = "books", key = "'allBooks'", unless = "#result == null || #result.isEmpty()")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id")
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book ID not found: " + id));
    }

    @Caching(evict = {
            @CacheEvict(value = "books", key = "'allBooks'"),
            @CacheEvict(value = "books", key = "'author_' + #book.authorId")
    })
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Caching(evict = {
            @CacheEvict(value = "books", key = "#id"),
            @CacheEvict(value = "books", key = "'allBooks'")
    })
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Cacheable(value = "books", key = "'author_' + #authorId", unless = "#result == null || #result.isEmpty()")
    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findBooksByAuthorId(authorId);
    }
}
