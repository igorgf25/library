package com.igor.library.service.impl;

import com.igor.library.exception.EntityAlreadyExist;
import com.igor.library.exception.EntityNotFound;
import com.igor.library.mockFactory.BookFactoryMock;
import com.igor.library.model.Author;
import com.igor.library.model.Book;
import com.igor.library.model.Category;
import com.igor.library.model.request.BookRequestDTO;
import com.igor.library.model.response.BookResponseDTO;
import com.igor.library.repository.AuthorRepository;
import com.igor.library.repository.BookRepository;
import com.igor.library.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    void testGetAll() {
        // 1. Preparation
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        PageImpl<Book> bookPage = new PageImpl<>(books);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(mapper.map(any(Book.class), eq(BookResponseDTO.class))).thenReturn(new BookResponseDTO());

        // 2. Execution
        Page<BookResponseDTO> bookResponseDTOList = bookService.getAll(0, 2, "title");

        // 3. Verification
        assertEquals(2, bookResponseDTOList.getContent().size());
    }

    @Test
    void testGetById() {
        // 1. Preparation
        Book book = new Book();
        book.setTitle("Book 1");

        BookResponseDTO bookResponseDTOMock = new BookResponseDTO();
        bookResponseDTOMock.setTitle("Book 1");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(mapper.map(any(Book.class), eq(BookResponseDTO.class))).thenReturn(bookResponseDTOMock);

        // 2. Execution
        BookResponseDTO bookResponseDTO = bookService.getById(1L);

        // 3. Verification
        assertEquals("Book 1", bookResponseDTO.getTitle());
    }

    @Test
    void testGetAllByAuthor() {
        // 1. Preparation
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        PageImpl<Book> bookPage = new PageImpl<>(books);

        when(bookRepository.findBooksByAuthor(any(Pageable.class), anyLong())).thenReturn(bookPage);

        // 2. Execution
        Page<BookResponseDTO> bookResponseDTOList = bookService.getAllByAuthor(0, 2, "title", 1L);

        // 3. Verification
        assertEquals(2, bookResponseDTOList.getContent().size());
    }

    @Test
    void testGetAllByCategory() {
        // 1. Preparation
        int page = 0;
        int size = 10;
        String sort = "title";
        Long categoryId = 1L;
        Book book = BookFactoryMock.FULL_BOOK;
        List<Book> books = new ArrayList<>();
        books.add(book);
        Page<Book> pageResult = new PageImpl<>(books);
        when(bookRepository.findBooksByCategory(any(PageRequest.class), anyLong())).thenReturn(pageResult);

        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(1L);
        bookResponseDTO.setTitle("Test Book");
        when(mapper.map(any(Book.class), any(Class.class))).thenReturn(bookResponseDTO);

        // 2. Execution
        Page<BookResponseDTO> result = bookService.getAllByCategory(page, size, sort, categoryId);

        // 3. Verification
        assertEquals(1, result.getContent().size());
        assertEquals(bookResponseDTO.getId(), result.getContent().get(0).getId());
        assertEquals(bookResponseDTO.getTitle(), result.getContent().get(0).getTitle());
    }

    @Test
    public void testCreateBook() {
        // 1. Preparation
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;
        when(bookRepository.findaByTitleAndAuthor(any(), any())).thenReturn(Optional.empty());
        when(mapper.map(bookRequestDTO, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(mapper.map(book, BookResponseDTO.class)).thenReturn(new BookResponseDTO());
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));

        // 2. Execution
        BookResponseDTO responseDTO = bookService.create(bookRequestDTO);

        // 3. Verification
        assertEquals(BookResponseDTO.class, responseDTO.getClass());
    }

    @Test
    public void testCreateBookThrowsEntityAlreadyExist() {
        // 1. Preparation
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;

        when(bookRepository.findaByTitleAndAuthor(any(), any())).thenReturn(Optional.of(book));
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));

        // 2. Execution and Verification
        assertThrows(EntityAlreadyExist.class, () -> {
            bookService.create(bookRequestDTO);
        });
    }

    @Test
    public void testCreateBookThrowsEntityNotFound() {
        // 1. Preparation
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;

        when(bookRepository.findaByTitleAndAuthor(any(), any())).thenReturn(Optional.empty());
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(authorRepository.findById(any())).thenReturn(Optional.empty());

        // 2. Execution and Verification
        assertThrows(EntityNotFound.class, () -> {
            bookService.create(bookRequestDTO);
        });
    }

    @Test
    public void testUpdateBook() {
        // 1. Preparation
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(mapper.map(bookRequestDTO, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(mapper.map(book, BookResponseDTO.class)).thenReturn(new BookResponseDTO());
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));

        // 2. Execution
        BookResponseDTO responseDTO = bookService.update(bookRequestDTO, 1L);

        // 3. Verification
        assertEquals(BookResponseDTO.class, responseDTO.getClass());
    }

    @Test
    public void testUpdateBookCategoryNotExist() {
        // 1. Preparation
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(categoryRepository.findById(any())).thenReturn(Optional.empty());
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));

        // 2. Execution and Verification
        assertThrows(EntityNotFound.class,() -> bookService.update(bookRequestDTO, 1L));
    }

    @Test
    public void testUpdateBookNotExist() {
        // 1. Preparation
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));

        // 2. Execution and Verification
        assertThrows(EntityNotFound.class,() -> bookService.update(bookRequestDTO, 1L));
    }

    @Test
    public void testDeleteBookSuccess() {
        // 1. Preparation
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        Optional<Book> bookOptional = Optional.of(book);

        when(bookRepository.findById(id)).thenReturn(bookOptional);

        // 2. Execution and Verification
        assertDoesNotThrow(() -> bookService.delete(id));
        verify(bookRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void testDeleteBookWhenBookNotFound() {
        // 1. Preparation
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // 2. Execution and Verification
        assertThrows(EntityNotFound.class, () -> bookService.delete(id));
    }

}
