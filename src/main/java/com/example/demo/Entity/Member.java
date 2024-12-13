package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @Pattern(regexp = "[a-zA-Z]+\s[a-zA-Z]+", message = "Enter first and last name")
    @NotBlank
    @Size(min = 6, message = "Size of first and last name must be 5")
    private String name;

    @Column(name = "membership_number")
    @Pattern(regexp = "M[0-9]+", message = "Membership number must start with M and have digits after")
    @NotBlank
    private String membershipNumber;

    @ManyToMany()
    @JoinTable(
            name = "borrowing",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> borrowedBooks = new HashSet<>();

    public Member(String name, String membershipNumber) {
        this.name = name;
        this.membershipNumber = membershipNumber;
    }

    public void addBorrowedBook(Book book) {
        this.borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        this.borrowedBooks.remove(book);
    }

}
