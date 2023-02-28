package com.igor.library.service;

import com.igor.library.model.Category;
import com.igor.library.model.request.CategoryRequestDTO;
import com.igor.library.model.response.CategoryResponseDTO;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<Category> getAll(int page, int size, String sort);

    CategoryResponseDTO create(CategoryRequestDTO category);
}
