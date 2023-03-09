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
    @DisplayName("Test get all books")
    void testGetAll() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        PageImpl<Book> bookPage = new PageImpl<>(books);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(mapper.map(any(Book.class), eq(BookResponseDTO.class))).thenReturn(new BookResponseDTO());

        Page<BookResponseDTO> bookResponseDTOList = bookService.getAll(0, 2, "title");

        assertEquals(2, bookResponseDTOList.getContent().size());
    }

    @Test
    @DisplayName("Test get book by ID")
    void testGetById() {
        Book book = new Book();
        book.setTitle("Book 1");

        BookResponseDTO bookResponseDTOMock = new BookResponseDTO();
        bookResponseDTOMock.setTitle("Book 1");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(mapper.map(any(Book.class), eq(BookResponseDTO.class))).thenReturn(bookResponseDTOMock);

        BookResponseDTO bookResponseDTO = bookService.getById(1L);

        assertEquals("Book 1", bookResponseDTO.getTitle());
    }

    @Test
    @DisplayName("Test get all books by author")
    void testGetAllByAuthor() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        PageImpl<Book> bookPage = new PageImpl<>(books);

        when(bookRepository.findBooksByAuthor(any(Pageable.class), anyLong())).thenReturn(bookPage);

        Page<BookResponseDTO> bookResponseDTOList = bookService.getAllByAuthor(0, 2, "title", 1L);

        assertEquals(2, bookResponseDTOList.getContent().size());
    }

    @Test
    @DisplayName("Test getAllByCategory")
    void testGetAllByCategory() {
        // given
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

        // when
        Page<BookResponseDTO> result = bookService.getAllByCategory(page, size, sort, categoryId);

        // then
        assertEquals(1, result.getContent().size());
        assertEquals(bookResponseDTO.getId(), result.getContent().get(0).getId());
        assertEquals(bookResponseDTO.getTitle(), result.getContent().get(0).getTitle());
    }

    @Test
    public void testCreateBook() {
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;
        when(bookRepository.findaByTitleAndAuthor(any(), any())).thenReturn(Optional.empty());
        when(mapper.map(bookRequestDTO, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(mapper.map(book, BookResponseDTO.class)).thenReturn(new BookResponseDTO());
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));

        BookResponseDTO responseDTO = bookService.create(bookRequestDTO);

        assertEquals(BookResponseDTO.class, responseDTO.getClass());
    }

    @Test
    public void testCreateBookThrowsEntityAlreadyExist() {
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;

        when(bookRepository.findaByTitleAndAuthor(any(), any())).thenReturn(Optional.of(book));
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));

        assertThrows(EntityAlreadyExist.class, () -> {
            bookService.create(bookRequestDTO);
        });
    }

    @Test
    public void testCreateBookThrowsEntityNotFound() {
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;

        when(bookRepository.findaByTitleAndAuthor(any(), any())).thenReturn(Optional.empty());
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(authorRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> {
            bookService.create(bookRequestDTO);
        });
    }

    @Test
    public void testUpdateBook() {
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(mapper.map(bookRequestDTO, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(mapper.map(book, BookResponseDTO.class)).thenReturn(new BookResponseDTO());
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));

        BookResponseDTO responseDTO = bookService.update(bookRequestDTO, 1L);

        assertEquals(BookResponseDTO.class, responseDTO.getClass());
    }

    @Test
    public void testUpdateBookCategoryNotExist() {
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(categoryRepository.findById(any())).thenReturn(Optional.empty());
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));

        assertThrows(EntityNotFound.class,() -> bookService.update(bookRequestDTO, 1L));
    }

    @Test
    public void testUpdateBookNotExist() {
        Book book = BookFactoryMock.FULL_BOOK;

        BookRequestDTO bookRequestDTO = BookFactoryMock.FULL_BOOK_REQUEST_DTO;

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category()));
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));

        assertThrows(EntityNotFound.class,() -> bookService.update(bookRequestDTO, 1L));
    }

    @Test
    void testDeleteBookSuccess() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        Optional<Book> bookOptional = Optional.of(book);

        when(bookRepository.findById(id)).thenReturn(bookOptional);

        assertDoesNotThrow(() -> bookService.delete(id));
        verify(bookRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void testDeleteBookWhenBookNotFound() {
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFound exception = assertThrows(EntityNotFound.class, () -> bookService.delete(id));

        assertEquals("Livro não existe no banco de dados.", exception.getMessage());
    }

}
