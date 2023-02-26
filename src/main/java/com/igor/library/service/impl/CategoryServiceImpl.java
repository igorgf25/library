package com.igor.library.service.impl;

import com.igor.library.model.Category;
import com.igor.library.repository.CategoryRepository;
import com.igor.library.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public Page<Category> getAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        return repository.findAll(pageable);
    }
}
