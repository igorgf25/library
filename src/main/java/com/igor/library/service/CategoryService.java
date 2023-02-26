package com.igor.library.service;

import com.igor.library.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    Page<Category> getAll(int page, int size, String sort);
}
