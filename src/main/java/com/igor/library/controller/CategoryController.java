package com.igor.library.controller;

import com.igor.library.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @GetMapping
    public ResponseEntity<Category> getCategories() {
        return null;
    }
}
