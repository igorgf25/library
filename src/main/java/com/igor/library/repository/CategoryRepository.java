package com.igor.library.repository;

import com.igor.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
