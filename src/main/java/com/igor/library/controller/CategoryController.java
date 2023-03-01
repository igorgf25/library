package com.igor.library.controller;

import com.igor.library.exception.EntityInvalid;
import com.igor.library.model.Category;
import com.igor.library.model.request.CategoryRequestDTO;
import com.igor.library.model.response.CategoryResponseDTO;
import com.igor.library.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "999") int size,
            @RequestParam(required = false, defaultValue = "id") String sort) {
        return new ResponseEntity<>(categoryService.getAll(page, size, sort), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO category, Errors errors) {

        if (errors.hasErrors()) {
            throw new EntityInvalid("Informações inválidas, por favor preencha todos os campos");
        }
        return new ResponseEntity<>(categoryService.create(category), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@Valid @RequestBody CategoryRequestDTO category,  Errors errors, @PathVariable(value = "id") Long id) {
        if (errors.hasErrors()) {
            throw new EntityInvalid("Informações inválidas, por favor preencha todos os campos");
        }
        return new ResponseEntity<>(categoryService.update(id, category), HttpStatus.OK);
    }
}
