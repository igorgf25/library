package com.igor.library.mockFactory;

import com.igor.library.model.Author;
import com.igor.library.model.Book;
import com.igor.library.model.Category;
import com.igor.library.model.request.CategoryRequestDTO;
import com.igor.library.model.response.CategoryResponseDTO;

import java.util.List;

public class CategoryFactoryMock {
    public final static Category FULL_CATEGORY = fullCategory();
    public final static CategoryResponseDTO FULL_CATEGORY_RESPONSE_DTO = fullCategoryResponseDTO();
    public final static CategoryRequestDTO FULL_CATEGORY_REQUEST_DTO = fullCategoryRequestDTO();

    private static CategoryRequestDTO fullCategoryRequestDTO() {
        CategoryRequestDTO category = new CategoryRequestDTO();
        category.setName("Drama");
        category.setDescription("Drama is a category or genre of narrative fiction (or semi-fiction) intended to be more serious than humorous in tone.");

        return category;
    }

    private static CategoryResponseDTO fullCategoryResponseDTO() {
        CategoryResponseDTO category = new CategoryResponseDTO();
        category.setId(1L);
        category.setName("Drama");
        category.setDescription("Drama is a category or genre of narrative fiction (or semi-fiction) intended to be more serious than humorous in tone.");

        return category;
    }

    private static Category fullCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Drama");
        category.setBooks(List.of(new Book()));
        category.setDescription("Drama is a category or genre of narrative fiction (or semi-fiction) intended to be more serious than humorous in tone.");

        return category;
    }
}
