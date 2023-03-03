package com.igor.library.repository;

import com.igor.library.model.Author;
import com.igor.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a WHERE " +
            "a.birthDate = :birthDate AND " +
            "a.name = :name")
    public Optional<Author> findAuthorByBirthDataAndName(@Param("birthDate") LocalDate birthDate, @Param("name") String name );
}
