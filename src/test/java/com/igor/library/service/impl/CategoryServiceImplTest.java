package com.igor.library.service.impl;

import com.igor.library.exception.EntityAlreadyExist;
import com.igor.library.exception.EntityNotFound;
import com.igor.library.mockFactory.CategoryFactoryMock;
import com.igor.library.model.Category;
import com.igor.library.model.request.CategoryRequestDTO;
import com.igor.library.model.response.CategoryResponseDTO;
import com.igor.library.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl service;

    @Mock
    private CategoryRepository repository;

    @Mock
    private ModelMapper mapper;

    @Test
    public void shouldReturnPageOfCategoryResponseDto() {
        // 1. Preparation
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Category category = CategoryFactoryMock.FULL_CATEGORY;
        Page<Category> pageResult = new PageImpl<>(List.of(category), pageable, 1);
        when(repository.findAll(any(Pageable.class))).thenReturn(pageResult);
        CategoryResponseDTO responseDTO = CategoryFactoryMock.FULL_CATEGORY_RESPONSE_DTO;
        when(mapper.map(any(Category.class), eq(CategoryResponseDTO.class))).thenReturn(responseDTO);

        // 2. Execution
        Page<CategoryResponseDTO> result = service.getAll(0, 10, "id");

        // 3. Preparation
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getContent().size());
        Assertions.assertEquals(category.getId(), result.getContent().get(0).getId());
        Assertions.assertEquals(category.getName(), result.getContent().get(0).getName());
        verify(repository, times(1)).findAll(pageable);
        verify(mapper, times(1)).map(any(Category.class), eq(CategoryResponseDTO.class));
    }

    @Test
    public void shouldCreateANewCategory() {
        // 1. Preparation
        CategoryRequestDTO requestDTO = new CategoryRequestDTO();
        requestDTO.setName("category name");
        Category category = CategoryFactoryMock.FULL_CATEGORY;
        when(repository.findCategoryByName(requestDTO.getName())).thenReturn(Optional.empty());
        when(mapper.map(any(CategoryRequestDTO.class), eq(Category.class))).thenReturn(category);
        when(mapper.map(any(Category.class), eq(CategoryResponseDTO.class))).thenReturn(new CategoryResponseDTO());
        when(repository.save(any(Category.class))).thenReturn(new Category());

        // 2. Execution
        CategoryResponseDTO result = service.create(requestDTO);

        // 3. Preparation
        Assertions.assertNotNull(result);
        verify(repository, Mockito.times(1)).findCategoryByName(requestDTO.getName());
        verify(repository, Mockito.times(1)).save(category);
        verify(mapper, Mockito.times(1)).map(requestDTO, Category.class);
    }

    @Test
    public void shouldNotCreateANewCategory() {
        // 1. Preparation
        CategoryRequestDTO requestDTO = new CategoryRequestDTO();
        requestDTO.setName("category name");
        Category category = new Category();
        category.setId(1L);
        category.setName(requestDTO.getName());
        Mockito.when(repository.findCategoryByName(requestDTO.getName())).thenReturn(Optional.of(category));

        // 2. Execution
        assertThrows(EntityAlreadyExist.class, () -> service.create(requestDTO), "Must throw an EntityAlreadyExist error");

    }

    @Test
    void testGetById() {
        // 1. Preparation
        Category category = CategoryFactoryMock.FULL_CATEGORY;

        CategoryResponseDTO categoryResponseDTO = CategoryFactoryMock.FULL_CATEGORY_RESPONSE_DTO;

        when(repository.findById(category.getId())).thenReturn(Optional.of(category));
        when(mapper.map(category, CategoryResponseDTO.class)).thenReturn(categoryResponseDTO);

        // 2. Execution
        CategoryResponseDTO responseDTO = service.getById(category.getId());

        assertNotNull(responseDTO);
        assertEquals(category.getId(), responseDTO.getId());
        assertEquals(category.getName(), responseDTO.getName());

        // 3. Preparation
        verify(repository, times(1)).findById(category.getId());
        verify(mapper, times(1)).map(category, CategoryResponseDTO.class);
    }

    @Test
    void testUpdate() {
        // 1. Preparation
        CategoryRequestDTO categoryRequest = CategoryFactoryMock.FULL_CATEGORY_REQUEST_DTO;

        Category category = CategoryFactoryMock.FULL_CATEGORY;

        CategoryResponseDTO categoryResponseDTO = CategoryFactoryMock.FULL_CATEGORY_RESPONSE_DTO;

        when(mapper.map(Mockito.any(CategoryRequestDTO.class), Mockito.eq(Category.class))).thenReturn(category);
        when(mapper.map(Mockito.any(Category.class), Mockito.eq(CategoryResponseDTO.class))).thenReturn(categoryResponseDTO);
        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(category));
        when(repository.save(Mockito.any(Category.class))).thenReturn(category);

        // 2. Execution
        CategoryResponseDTO categoryResponse = service.update(1L, categoryRequest);

        // 3. Preparation
        assertEquals(categoryResponse.getId(), category.getId());
        assertEquals(categoryResponse.getName(), categoryRequest.getName());
    }

    @Test
    void testShouldNotUpdateUpdate() {
        // 1. Preparation
        CategoryRequestDTO categoryRequest = new CategoryRequestDTO();
        categoryRequest.setName("New Category");

        Category category = CategoryFactoryMock.FULL_CATEGORY;

        CategoryResponseDTO categoryResponseDTO = CategoryFactoryMock.FULL_CATEGORY_RESPONSE_DTO;

        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // 2. Execution and Verification
        assertThrows(EntityNotFound.class, () -> service.update(1L, categoryRequest)) ;
    }

    @Test
    public void deleteCategoryExistsShouldDeleteSuccessfully() {
        // 1. Preparation
        Category category = CategoryFactoryMock.FULL_CATEGORY;
        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(category));

        // 2. Execution
        service.delete(Mockito.any(Long.class));

        // 3. Preparation
        verify(repository, times(1)).deleteById(Mockito.any(Long.class));
    }

    @Test
    public void deleteCategoryDoesNotExistShouldThrowEntityNotFound() {
        // 1. Preparation
        Long categoryId = 1L;
        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // 2. Execution and Verification
        assertThrows(EntityNotFound.class, () -> service.delete(categoryId));
    }
}