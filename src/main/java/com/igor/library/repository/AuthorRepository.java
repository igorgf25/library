package com.igor.library.repository;

import com.igor.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Category, Long> {
}
