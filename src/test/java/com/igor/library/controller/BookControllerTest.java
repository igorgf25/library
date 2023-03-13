package com.igor.library.controller;

import com.igor.library.exception.CustomRestExceptionHandler;
import com.igor.library.mockFactory.BookFactoryMock;
import com.igor.library.model.request.BookRequestDTO;
import com.igor.library.service.BookService;
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

class BookControllerTest {
    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new CustomRestExceptionHandler())
                .build();
    }

    @Test
    public void getAllShouldReturnListOfBooks() throws Exception {
        // 1. Preparation
        when(bookService.getAll(anyInt(), anyInt(), anyString())).thenReturn(Page.empty());

        // 2. Execution and Verification
        mockMvc.perform(get("/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllShouldReturnListOfBooksByAuthor() throws Exception {
        // 1. Preparation
        when(bookService.getAllByAuthor(anyInt(), anyInt(), anyString(), anyLong())).thenReturn(Page.empty());

        // 2. Execution and Verification
        mockMvc.perform(get("/book/author/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllShouldReturnListOfBooksByCategory() throws Exception {
        // 1. Preparation
        when(bookService.getAllByCategory(anyInt(), anyInt(), anyString(), anyLong())).thenReturn(Page.empty());

        // 2. Execution and Verification
        mockMvc.perform(get("/book/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdShouldReturnBookById() throws Exception {
        // 1. Preparation
        when(bookService.getById(anyLong())).thenReturn(BookFactoryMock.FULL_BOOK_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(get("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    public void createShouldReturnCreatedBook() throws Exception {
        // 1. Preparation
        JSONObject json = new JSONObject();
        json.put("title", "Test");
        json.put("description", "Test");
        json.put("numberOfPages", "139");
        json.put("language", "English");
        json.put("publisher", "Test");
        json.put("publicationDate", "1997-05-26");
        json.put("author", 1);
        json.put("category", 1);

        when(bookService.create(any(BookRequestDTO.class))).thenReturn(BookFactoryMock.FULL_BOOK_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    public void createWithInvalidBookShouldReturnBadRequest() throws Exception {
        // 1. Execution and Verification
        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBook() throws Exception {
        // 1. Preparation
        JSONObject json = new JSONObject();
        json.put("title", "Test");
        json.put("description", "Test");
        json.put("numberOfPages", "139");
        json.put("language", "English");
        json.put("publisher", "Test");
        json.put("publicationDate", "1997-05-26");
        json.put("author", 1);
        json.put("category", 1);

        when(bookService.update(any(), any())).thenReturn(BookFactoryMock.FULL_BOOK_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(put("/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    public void updateWithInvalidBookShouldReturnBadRequest() throws Exception {
        // 1. Execution and Verification
        mockMvc.perform(put("/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithInvalidBookIdShouldReturnMethodNotAllowed() throws Exception {
        // 1. Execution and Verification
        mockMvc.perform(put("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"\" }"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deleteShouldReturnOkWhenBookExists() throws Exception {
        // 1. Execution and Verification
        Long id = 1L;
        doNothing().when(bookService).delete(id);
        mockMvc.perform(delete("/book/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookService).delete(id);
    }

}