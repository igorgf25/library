package com.igor.library.repository;

import com.igor.library.model.Author;
import com.igor.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT b FROM Book b WHERE " +
            "b.author.id = :id",
            countQuery = "SELECT COUNT(b) FROM Book b WHERE " +
            "b.author.id = :id")
    public Page<Book> findBooksByAuthor(Pageable pageable, Long id);

    @Query(value = "SELECT b FROM Book b WHERE " +
            "b.category.id = :id",
            countQuery = "SELECT COUNT(b) FROM Book b WHERE " +
            "b.category.id = :id")
    Page<Book> findBooksByCategory(Pageable pageable, Long id);

    @Query("SELECT b FROM Book b WHERE " +
            "b.title = :title AND " +
            "b.author.id = :author")
    Optional<Book> findaByTitleAndAuthor(String title, Long author);
}
