package com.igor.library.mockFactory;

import com.igor.library.model.Author;
import com.igor.library.model.Book;
import com.igor.library.model.Category;
import com.igor.library.model.request.BookRequestDTO;
import com.igor.library.model.response.AuthorResponseDTO;
import com.igor.library.model.response.BookResponseDTO;
import com.igor.library.model.response.CategoryResponseDTO;

import java.time.LocalDate;

public class BookFactoryMock {

    public final static Book FULL_BOOK = fullBook();
    public final static BookRequestDTO FULL_BOOK_REQUEST_DTO = fullBookRequestDTO();
    public final static BookResponseDTO FULL_BOOK_RESPONSE_DTO = fullBookResponseDTO();

    private static BookResponseDTO fullBookResponseDTO() {
        BookResponseDTO book = new BookResponseDTO();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setCategory(new CategoryResponseDTO());
        book.setAuthor(new AuthorResponseDTO());
        book.setLanguage("English");
        book.setDescription("A test book");
        book.setPublisher("Test publisher");
        book.setNumberOfPages("10");
        book.setPublicationDate(LocalDate.now());
        return book;
    }

    private static BookRequestDTO fullBookRequestDTO() {
        BookRequestDTO book = new BookRequestDTO();
        book.setTitle("Test Book");
        book.setCategory(1L);
        book.setAuthor(1L);
        book.setLanguage("English");
        book.setDescription("A test book");
        book.setPublisher("Test publisher");
        book.setNumberOfPages("10");
        book.setPublicationDate(LocalDate.now());
        return book;
    }

    private static Book fullBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setCategory(new Category());
        book.setAuthor(new Author());
        book.setLanguage("English");
        book.setDescription("A test book");
        book.setPublisher("Test publisher");
        book.setNumberOfPages("10");
        book.setPublicationDate(LocalDate.now());
        return book;
    }


}
