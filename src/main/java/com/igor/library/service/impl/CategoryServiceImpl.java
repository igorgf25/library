package com.igor.library.service.impl;

import com.igor.library.exception.EntityAlreadyExist;
import com.igor.library.exception.EntityNotFound;
import com.igor.library.model.Category;
import com.igor.library.model.request.CategoryRequestDTO;
import com.igor.library.model.response.CategoryResponseDTO;
import com.igor.library.repository.CategoryRepository;
import com.igor.library.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final ModelMapper mapper;

    @Override
    public Page<CategoryResponseDTO> getAll(int page, int size, String sort) {
        log.info("CategoryServiceImpl.getAll getting all the categories in the database");
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        Page<Category> resultPage = repository.findAll(pageable);

        return resultPage.map( obj -> mapper.map(obj, CategoryResponseDTO.class));
    }

    @Override
    public CategoryResponseDTO getById(Long id) {
        log.info("CategoryServiceImpl.getById getting the category with id " + id + ".");
        Category category = repository.findById(id).orElseThrow(() -> new EntityNotFound("Category with the " + id + " not found."));

        return mapper.map(category, CategoryResponseDTO.class);
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO category) {
        log.info("CategoryServiceImpl.create inserting a category in the database");
        Optional<Category> alreadyExists = repository.findCategoryByName(category.getName());

        if (alreadyExists.isPresent()) {
            throw new EntityAlreadyExist("Category already exists.");
        }

        Category categoryResponse = repository.save(mapper.map(category, Category.class));

        return mapper.map(categoryResponse, CategoryResponseDTO.class);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequest) {
        log.info("CategoryServiceImpl.update updating the category with id " + id + ".");

        Optional<Category> categoryVerification = repository.findById(id);

        if (categoryVerification.isEmpty()) {
            throw new EntityNotFound("Category not found.");
        }

        Category category = mapper.map(categoryRequest, Category.class);
        category.setId(id);

        Category categoryResponse = repository.save(category);

        return mapper.map(categoryResponse, CategoryResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        log.info("CategoryServiceImpl.delete deleting the category with id " + id + ".");

        Optional<Category> authorVerification = repository.findById(id);

        if (authorVerification.isEmpty()) {
            throw new EntityNotFound("Category nor found.");
        }

        repository.deleteById(id);
    }
}
