package com.example.demo.Service;

import com.example.demo.Entity.Author;
import com.example.demo.Repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
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

class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuthors() {
        List<Author> mockAuthors = Arrays.asList(
                new Author("Author1"),
                new Author("Author2")
        );
        when(authorRepository.findAll()).thenReturn(mockAuthors);

        List<Author> authors = authorService.getAllAuthors();

        assertNotNull(authors);
        assertEquals(2, authors.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testGetAuthorById() {
        Long id = 1L;
        Author mockAuthor = new Author("Author1");
        when(authorRepository.findById(id)).thenReturn(Optional.of(mockAuthor));

        Author author = authorService.getAuthorById(id);

        assertNotNull(author);
        assertEquals("Author1", author.getName());
        verify(authorRepository, times(1)).findById(id);
    }

    @Test
    void testGetAuthorByIdNotFound() {
        Long id = 99L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        try {
            Author author = authorService.getAuthorById(id);
        } catch (EntityNotFoundException ex) {
            assertEquals("Author not found with ID: " + id, ex.getMessage());
        }

        verify(authorRepository, times(1)).findById(id);
    }

    @Test
    void testSaveAuthor() {
        Author authorToSave = new Author("New Author");
        Author savedAuthor = new Author("New Author");
        when(authorRepository.save(authorToSave)).thenReturn(savedAuthor);

        Author result = authorService.saveAuthor(authorToSave);

        assertNotNull(result.getId());
        assertEquals("New Author", result.getName());
        verify(authorRepository, times(1)).save(authorToSave);
    }

    @Test
    void testDeleteAuthor() {
        Long id = 1L;

        authorService.deleteAuthor(id);

        verify(authorRepository, times(1)).deleteById(id);
    }
}
