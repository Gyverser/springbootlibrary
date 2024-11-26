package com.example.demo.Service;

import com.example.demo.Entity.Book;
import com.example.demo.Repository.BookRepository;
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

class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        List<Book> mockBooks = Arrays.asList(
                new Book("Book1", "32131293291", 15),
                new Book("Book2", "3299911992", 5)
        );

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("Book1", books.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() {
        Long id = 1L;
        Book mockBook = new Book("Book1", "32983219831", 15);
        mockBook.setId(id);

        when(bookRepository.findById(id)).thenReturn(Optional.of(mockBook));

        Book book = bookService.getBookById(id);

        assertNotNull(book);
        assertEquals("Book1", book.getTitle());
        assertEquals(id, book.getId());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void testSaveBook() {
        Book bookToSave = new Book("New Book", "98132981329", 4);
        Book savedBook = new Book("New Book", "8120182401", 460);
        savedBook.setId(1L);

        when(bookRepository.save(bookToSave)).thenReturn(savedBook);

        Book result = bookService.saveBook(bookToSave);

        assertNotNull(result.getId());
        assertEquals("New Book", result.getTitle());
        verify(bookRepository, times(1)).save(bookToSave);
    }

    @Test
    void testDeleteBook() {
        Long id = 1L;

        bookService.deleteBook(id);

        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetBooksByAuthorId() {
        Long authorId = 1L;
        List<Book> mockBooks = Arrays.asList(
                new Book("Book1", "74782141", 13),
                new Book("Book2", "932131922913", 16)
        );

        when(bookRepository.findBooksByAuthorId(authorId)).thenReturn(mockBooks);

        List<Book> books = bookService.getBooksByAuthorId(authorId);

        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("Book1", books.get(0).getTitle());
        verify(bookRepository, times(1)).findBooksByAuthorId(authorId);
    }
}