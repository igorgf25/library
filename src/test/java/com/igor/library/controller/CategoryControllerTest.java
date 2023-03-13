package com.igor.library.controller;

import com.igor.library.exception.CustomRestExceptionHandler;
import com.igor.library.mockFactory.CategoryFactoryMock;
import com.igor.library.model.request.CategoryRequestDTO;
import com.igor.library.model.response.AuthorResponseDTO;
import com.igor.library.service.CategoryService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new CustomRestExceptionHandler())
                .build();
    }

    @Test
    public void getAllShouldReturnListOfCategories() throws Exception {
        // 1. Preparation
        when(categoryService.getAll(anyInt(), anyInt(), anyString())).thenReturn(Page.empty());

        // 2. Execution and Verification
        mockMvc.perform(get("/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdShouldReturnCategoryById() throws Exception {
        // 1. Preparation
        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setName("John");
        authorResponseDTO.setId(1L);

        when(categoryService.getById(anyLong())).thenReturn(CategoryFactoryMock.FULL_CATEGORY_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(get("/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Drama"));
    }

    @Test
    public void createShouldReturnCreatedCategory() throws Exception {
        // 1. Preparation
        JSONObject json = new JSONObject();
        json.put("name", "Test");
        json.put("description", "Test");

        when(categoryService.create(any(CategoryRequestDTO.class))).thenReturn(CategoryFactoryMock.FULL_CATEGORY_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Drama"));
    }

    @Test
    public void createWithInvalidCategoryShouldReturnBadRequest() throws Exception {
        // 1. Execution and Verification
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCategory() throws Exception {
        // 1. Preparation
        JSONObject json = new JSONObject();
        json.put("name", "Test");
        json.put("description", "Test");

        when(categoryService.update(any(), any())).thenReturn(CategoryFactoryMock.FULL_CATEGORY_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(put("/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Drama"));
    }

    @Test
    public void updateWithInvalidCategoryShouldReturnBadRequest() throws Exception {
        // 1. Execution and Verification
        mockMvc.perform(put("/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithInvalidCategoryIdShouldReturnMethodNotAllowed() throws Exception {
        // 1. Execution and Verification
        mockMvc.perform(put("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"\" }"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deleteShouldReturnOkWhenCategoryExists() throws Exception {
        // 1. Execution and Verification
        Long id = 1L;
        doNothing().when(categoryService).delete(id);
        mockMvc.perform(delete("/category/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(categoryService).delete(id);
    }

}