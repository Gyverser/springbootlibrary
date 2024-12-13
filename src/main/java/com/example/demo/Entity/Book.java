package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.parameters.P;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    @NotBlank(message = "Enter a title")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 letters")
    @Pattern(regexp = "[a-zA-Z0-9 '\\-]+", message = "Title must only contain letters")
    private String title;

    @Column(name = "isbn")
    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "\\d+", message = "ISBN must be numerical")
    private String isbn;

    @Column(name = "copies")
    @Min(value = 1, message = "Copies must be at least 1")
    private int copies;

    @Column(name = "author_id", insertable = false, updatable = false)
    @NotNull
    private Long authorId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(mappedBy = "borrowedBooks")
    private Set<Member> borrowers = new HashSet<>();

    public Book(String title, String isbn, int copies) {
        this.title = title;
        this.isbn = isbn;
        this.copies = copies;
    }

}
