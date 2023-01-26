package com.igor.library.model.request;

import com.igor.library.model.response.AuthorResponseDTO;
import com.igor.library.model.response.CategoryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {
    private Long id;

    private String title;

    private String description;

    private String numberOfPages;

    private String language;

    private String publisher;

    private LocalDate publicationDate;

    private AuthorRequestDTO author;

    private CategoryRequestDTO category;
}
