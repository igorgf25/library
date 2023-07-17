package com.igor.library.controller;

import com.igor.library.exception.CustomRestExceptionHandler;
import com.igor.library.mockFactory.AuthorFactoryMock;
import com.igor.library.model.request.AuthorRequestDTO;
import com.igor.library.model.response.AuthorResponseDTO;
import com.igor.library.service.AuthorService;
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

class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .setControllerAdvice(new CustomRestExceptionHandler())
                .build();
    }

    @Test
    public void getAllShouldReturnListOfAuthors() throws Exception {
        // 1. Preparation
        when(authorService.getAll(anyInt(), anyInt(), anyString())).thenReturn(Page.empty());

        // 2. Execution and Verification
        mockMvc.perform(get("/author")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdShouldReturnAuthorById() throws Exception {
        // 1. Preparation
        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setName("John");
        authorResponseDTO.setId(1L);

        when(authorService.getById(anyLong())).thenReturn(authorResponseDTO);

        // 2. Execution and Verification
        mockMvc.perform(get("/author/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void createShouldReturnCreatedAuthor() throws Exception {
        // 1. Preparation
        JSONObject json = new JSONObject();
        json.put("name", "Test");
        json.put("nationality", "American");
        json.put("birthDate", "25/04/2000");

        when(authorService.create(any(AuthorRequestDTO.class))).thenReturn(AuthorFactoryMock.FULL_AUTHOR_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Stephen King"));
    }

    @Test
    public void createWithInvalidAuthorShouldReturnBadRequest() throws Exception {
        // 1. Execution and Verification
        mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        // 1. Preparation
        JSONObject json = new JSONObject();
        json.put("name", "Test");
        json.put("nationality", "American");
        json.put("birthDate", "25/04/2000");

        when(authorService.update(any(), any())).thenReturn(AuthorFactoryMock.FULL_AUTHOR_RESPONSE_DTO);

        // 2. Execution and Verification
        mockMvc.perform(put("/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Stephen King"));
    }

    @Test
    public void updateWithInvalidAuthorShouldReturnBadRequest() throws Exception {
        // 1. Execution and Verification
        mockMvc.perform(put("/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithInvalidAuthorIdShouldReturnMethodNotAllowed() throws Exception {
        // 1. Execution and Verification
        mockMvc.perform(put("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"\" }"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deleteShouldReturnOkWhenAuthorExists() throws Exception {
        // 1. Execution and Verification
        Long id = 1L;
        doNothing().when(authorService).delete(id);
        mockMvc.perform(delete("/author/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(authorService).delete(id);
    }
}