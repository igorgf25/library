package com.igor.library.service.impl;

import com.igor.library.exception.EntityAlreadyExist;
import com.igor.library.exception.EntityNotFound;
import com.igor.library.model.Author;
import com.igor.library.model.Book;
import com.igor.library.model.Category;
import com.igor.library.model.request.BookRequestDTO;
import com.igor.library.model.response.BookResponseDTO;
import com.igor.library.repository.AuthorRepository;
import com.igor.library.repository.BookRepository;
import com.igor.library.repository.CategoryRepository;
import com.igor.library.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final ModelMapper mapper;

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Page<BookResponseDTO> getAll(int page, int size, String sort) {
        log.info("BookServiceImpl.getAll getting all the books in the database");
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        Page<Book> resultPage = repository.findAll(pageable);
        return resultPage.map(obj -> mapper.map(obj, BookResponseDTO.class));
    }

    @Override
    public Page<BookResponseDTO> getAllByAuthor(int page, int size, String sort, Long id) {
        log.info("BookServiceImpl.getAllByAuthor getting all the books with authorId " + id + ".");
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        Page<Book> resultPage = repository.findBooksByAuthor(pageable, id);
        return resultPage.map(obj -> mapper.map(obj, BookResponseDTO.class));
    }

    @Override
    public Page<BookResponseDTO> getAllByCategory(int page, int size, String sort, Long id) {
        log.info("BookServiceImpl.getAllByCategory getting all the books with categoryId " + id + ".");
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        Page<Book> resultPage = repository.findBooksByCategory(pageable, id);
        return resultPage.map(obj -> mapper.map(obj, BookResponseDTO.class));
    }

    @Override
    public BookResponseDTO getById(Long id) {
        log.info("BookServiceImpl.getById getting the book with id " + id + ".");
        Book book = repository.findById(id).orElseThrow(() -> new EntityNotFound("Book with the id " + id + " not found."));
        return mapper.map(book, BookResponseDTO.class);
    }

    @Override
    public BookResponseDTO create(BookRequestDTO bookRequest) {
        log.info("BookServiceImpl.create inserting a book in the database");
        Optional<Book> bookOptional = repository.findaByTitleAndAuthor(bookRequest.getTitle(), bookRequest.getAuthor());
        Optional<Category> categoryOptional = categoryRepository.findById(bookRequest.getCategory());
        Optional<Author> authorOptional = authorRepository.findById(bookRequest.getAuthor());

        if (categoryOptional.isEmpty() || authorOptional.isEmpty()) {
            throw new EntityNotFound("Category or author not found.");
        }

        if (bookOptional.isPresent()) {
            throw new EntityAlreadyExist("Book already exists.");
        }

        Book book = mapper.map(bookRequest, Book.class);
        book.setAuthor(authorOptional.get());
        book.setCategory(categoryOptional.get());

        Book savedBook = repository.save(book);
        return mapper.map(savedBook, BookResponseDTO.class);
    }

    @Override
    public BookResponseDTO update(BookRequestDTO bookRequest, Long id) {
        log.info("BookServiceImpl.update updating the book with id " + id + ".");
        Optional<Book> bookOptional = repository.findById(id);
        Optional<Category> categoryOptional = categoryRepository.findById(bookRequest.getCategory());
        Optional<Author> authorOptional = authorRepository.findById(bookRequest.getAuthor());

        if (categoryOptional.isEmpty() || authorOptional.isEmpty()) {
            throw new EntityNotFound("Category or author not found.");
        }

        if (bookOptional.isEmpty()) {
            throw new EntityNotFound("Book with the id " + id + " not found.");
        }

        Book book = mapper.map(bookRequest, Book.class);
        book.setId(id);
        book.setAuthor(authorOptional.get());
        book.setCategory(categoryOptional.get());

        Book savedBook = repository.save(book);
        return mapper.map(savedBook, BookResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        log.info("BookServiceImpl.delete deleting the book with id " + ".");
        Optional<Book> bookVerification = repository.findById(id);
        if (bookVerification.isEmpty()) {
            throw new EntityNotFound("Book not found.");
        }

        repository.deleteById(id);
    }
}
