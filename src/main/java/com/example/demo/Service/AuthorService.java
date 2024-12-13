package com.example.demo.Service;

import com.example.demo.Entity.Author;
import com.example.demo.Repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Cacheable(value = "authors", key = "'allAuthors'", unless = "#result == null || #result.isEmpty()")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Cacheable(value = "authors", key = "#id")
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found with ID: " + id));
    }

    @CacheEvict(value = "authors", key = "'allAuthors'")
    @Transactional
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Caching(evict = {
            @CacheEvict(value = "authors", key = "#id"),
            @CacheEvict(value = "authors", key = "'allAuthors'")
    })
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
