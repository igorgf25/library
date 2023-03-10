package com.igor.library.service.impl;

import com.igor.library.exception.EntityAlreadyExist;
import com.igor.library.exception.EntityNotFound;
import com.igor.library.mockFactory.AuthorFactoryMock;
import com.igor.library.model.Author;
import com.igor.library.model.request.AuthorRequestDTO;
import com.igor.library.model.response.AuthorResponseDTO;
import com.igor.library.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthorServiceImplTest {

    @InjectMocks
    private  AuthorServiceImpl service;

    @Mock
    private AuthorRepository repository;

    @Mock
    private ModelMapper mapper;

    @Test
    public void testShouldReturnAPageOfAuthors() {
        // 1. Preparation
        List<Author> content = new ArrayList<>();
        content.add(AuthorFactoryMock.FULL_AUTHOR);
        Page<Author> authorsPage = new PageImpl<>(content);
        when(repository.findAll(any(Pageable.class))).thenReturn(authorsPage);
        // 2. Execution
        Page<AuthorResponseDTO> result = service.getAll(0, 10, "id");
        // 3. Verification
        Assertions.assertEquals(authorsPage.getSize() , result.getSize(), "Must be equal");
    }

    @Test
    public void testShouldReturnAnAuthorById() {
        // 1. Preparation
        Author mockAuthor = AuthorFactoryMock.FULL_AUTHOR;
        when(repository.findById(1L)).thenReturn(Optional.of(mockAuthor));
        when(mapper.map(any(Author.class), eq(AuthorResponseDTO.class))).thenReturn(AuthorFactoryMock.FULL_AUTHOR_RESPONSE_DTO);
        // 2. Execution
        AuthorResponseDTO result = service.getById(1L);
        // 3. Verification
        Assertions.assertEquals(AuthorFactoryMock.FULL_AUTHOR.getName() , result.getName(), "Must be equal");
    }

    @Test
    void testGetByIdShouldThrownAnErrorNotFoundAuthor() {
        // 1. Preparation
        when(repository.findById(1L)).thenReturn(Optional.empty());
        // 2. Execution and Verification
        Assertions.assertThrows(EntityNotFound.class, () -> service.getById(1L), "Should thrown an EntityNotFound error");
        // 3. Verification
    }

    @Test
    void testShouldReturnAnAuthorWhenCreated() {
        // 1. Preparation
        Author mockAuthor = AuthorFactoryMock.FULL_AUTHOR;
        when(repository.findAuthorByBirthDataAndName(any(LocalDate.class), any(String.class))).thenReturn(Optional.empty());
        when(repository.save(any(Author.class))).thenReturn(mockAuthor);
        when(mapper.map(any(AuthorRequestDTO.class), eq(Author.class))).thenReturn(AuthorFactoryMock.FULL_AUTHOR);
        when(mapper.map(any(Author.class), eq(AuthorResponseDTO.class))).thenReturn(AuthorFactoryMock.FULL_AUTHOR_RESPONSE_DTO);
        // 2. Execution
        AuthorResponseDTO result = service.create(AuthorFactoryMock.FULL_AUTHOR_REQUEST_DTO);
        // 3. Verification
        Assertions.assertEquals(AuthorFactoryMock.FULL_AUTHOR_RESPONSE_DTO.getName() , result.getName(), "Must be equal");
    }

    @Test
    void testShouldThrownAnEntityAlreadyExistWhenAuthorAlreadExists() {
        // 1. Preparation
        Author mockAuthor = AuthorFactoryMock.FULL_AUTHOR;
        when(repository.findAuthorByBirthDataAndName(any(LocalDate.class), any(String.class))).thenReturn(Optional.of(AuthorFactoryMock.FULL_AUTHOR));
        // 2. Execution and Verification
        assertThrows(EntityAlreadyExist.class, () -> service.create(AuthorFactoryMock.FULL_AUTHOR_REQUEST_DTO));
    }

    @Test
    void testShouldReturnAnAuthorWhenUpdated() {
        // 1. Preparation
        Author mockAuthor = AuthorFactoryMock.FULL_AUTHOR;
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(AuthorFactoryMock.FULL_AUTHOR));
        when(repository.save(any(Author.class))).thenReturn(mockAuthor);
        when(mapper.map(any(AuthorRequestDTO.class), eq(Author.class))).thenReturn(AuthorFactoryMock.FULL_AUTHOR);
        when(mapper.map(any(Author.class), eq(AuthorResponseDTO.class))).thenReturn(AuthorFactoryMock.FULL_AUTHOR_RESPONSE_DTO);
        // 2. Execution
        AuthorResponseDTO result = service.update(1L, AuthorFactoryMock.FULL_AUTHOR_REQUEST_DTO);
        // 3. Verification
        Assertions.assertEquals(AuthorFactoryMock.FULL_AUTHOR_RESPONSE_DTO.getName() , result.getName(), "Must be equal");
    }

    @Test
    void testShouldThrownAnEntityAlreadyExistWhenAuthorDontExistUpdate() {
        // 1. Preparation
        Author mockAuthor = AuthorFactoryMock.FULL_AUTHOR;
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());
        // 2. Execution and Verification
        Assertions.assertThrows(EntityNotFound.class, () -> service.update(1L, AuthorFactoryMock.FULL_AUTHOR_REQUEST_DTO));
    }

    @Test
    void testShouldDeleteAnAuthor() {
        // 1. Preparation
        Author mockAuthor = AuthorFactoryMock.FULL_AUTHOR;
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(AuthorFactoryMock.FULL_AUTHOR));
        // 2. Execution
        service.delete(1L);
        // 3. Verification
        verify(repository).deleteById(any(Long.class));
    }

    @Test
    void testShouldThrowAnEntityNotFoundErrorWhenAuthor() {
        // 1. Preparation
        Author mockAuthor = AuthorFactoryMock.FULL_AUTHOR;
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());
        // 2. Execution and Verification
        Assertions.assertThrows(EntityNotFound.class, () -> service.delete(1L));
    }

}