package com.example.demo.Repository;

import com.example.demo.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b from Book b JOIN FETCH b.author WHERE b.author.id=:authorId")
    List<Book> findBooksByAuthorId(@Param("authorId") Long authorId);

}
