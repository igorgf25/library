package com.igor.library.mockFactory;

import com.igor.library.model.Author;
import com.igor.library.model.request.AuthorRequestDTO;
import com.igor.library.model.response.AuthorResponseDTO;

import java.time.LocalDate;

public class AuthorFactoryMock {

    public final static Author FULL_AUTHOR = fullAuthor();
    public final static AuthorResponseDTO FULL_AUTHOR_RESPONSE_DTO = fullAuthorResponseDTO();
    public final static AuthorRequestDTO FULL_AUTHOR_REQUEST_DTO = fullAuthorRequestDTO();

    private static Author fullAuthor() {
        Author author = new Author();
        author.setId(2L);
        author.setName("Stephen King");
        author.setNationality("American");
        author.setBirthDate(LocalDate.parse("1947-09-24"));
        return author;
    }

    private static AuthorResponseDTO fullAuthorResponseDTO() {
        AuthorResponseDTO author = new AuthorResponseDTO();
        author.setId(2L);
        author.setName("Stephen King");
        author.setNationality("American");
        author.setBirthDate(LocalDate.parse("1947-09-24"));
        return author;
    }

    private static AuthorRequestDTO fullAuthorRequestDTO() {
        AuthorRequestDTO author = new AuthorRequestDTO();
        author.setName("Stephen King");
        author.setNationality("American");
        author.setBirthDate(LocalDate.parse("1947-09-24"));
        return author;
    }
}
