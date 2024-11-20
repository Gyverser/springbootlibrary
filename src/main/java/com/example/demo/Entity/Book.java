package com.example.demo.Entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="title")
    private String title;

    @Column(name="isbn")
    private String isbn;

    @Column(name="copies")
    private int copies;

    @Column(name="author_id", insertable = false, updatable = false)
    private Long authorId;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @ManyToMany(mappedBy = "borrowedBooks")
    private Set<Member> memberList = new HashSet<>();

    public Book() {

    }

    public Set<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(Set<Member> memberList) {
        this.memberList = memberList;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book(String title, String isbn, int copies, Long authorId) {
        this.title = title;
        this.isbn = isbn;
        this.copies = copies;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void addMember(Member member) {
        this.memberList.add(member);
        member.getBorrowedBooks().add(this);
    }

    public void removeMember(Member member) {
        this.memberList.remove(member);
        member.getBorrowedBooks().remove(this);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", copies=" + copies +
                '}';
    }
}
