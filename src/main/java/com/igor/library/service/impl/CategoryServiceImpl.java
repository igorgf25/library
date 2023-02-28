package com.igor.library.service.impl;

import com.igor.library.exception.EntityAlreadyExist;
import com.igor.library.exception.EntityNotFound;
import com.igor.library.model.Category;
import com.igor.library.model.request.CategoryRequestDTO;
import com.igor.library.model.response.CategoryResponseDTO;
import com.igor.library.repository.CategoryRepository;
import com.igor.library.service.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final ModelMapper mapper;

    @Override
    public Page<Category> getAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        return repository.findAll(pageable);
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO category) {
        Optional<Category> alreadyExists = repository.findCategoryByName(category.getName());

        if (alreadyExists.isPresent()) {
            throw new EntityAlreadyExist("Categoria já registrada no banco de dados.");
        }

        Category categoryResponse = repository.save(mapper.map(category, Category.class));

        return mapper.map(categoryResponse, CategoryResponseDTO.class);
    }

    @Override
    public CategoryResponseDTO getById(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new EntityNotFound("Categoria com o id: " + id + " não encontrada."));

        return mapper.map(category, CategoryResponseDTO.class);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequest) {
        Optional<Category> alreadyExists = repository.findById(id);

        if (alreadyExists.isEmpty()) {
            throw new EntityNotFound("Categoria não existe no banco de dados.");
        }

        Category category = mapper.map(categoryRequest, Category.class);
        category.setId(id);

        Category categoryResponse = repository.save(category);

        return mapper.map(categoryResponse, CategoryResponseDTO.class);
    }
}
