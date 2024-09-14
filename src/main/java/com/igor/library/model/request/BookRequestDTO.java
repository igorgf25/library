package com.igor.library.model.request;

import com.igor.library.model.Author;
import com.igor.library.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String numberOfPages;

    @NotBlank
    private String language;

    @NotBlank
    private String publisher;

    @NotNull
    private LocalDate publicationDate;

    @NotNull
    private Long author;

    @NotNull
    private Long category;
}
