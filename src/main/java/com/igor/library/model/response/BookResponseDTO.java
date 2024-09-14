package com.igor.library.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private Long id;

    private String title;

    private String description;

    private String numberOfPages;

    private String language;

    private String publisher;

    private LocalDate publicationDate;

    private AuthorResponseDTO author;

    private CategoryResponseDTO category;
}
