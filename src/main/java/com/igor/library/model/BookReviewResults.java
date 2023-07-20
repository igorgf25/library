package com.igor.library.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookReviewResults {

    private String url;

    @JsonProperty("publication_dt")
    private String publicationDt;

    private String byline;

    @JsonProperty("book_title")
    private String bookTitle;

    @JsonProperty("book_author")
    private String bookAuthor;

    private String summary;

    private String uuid;

    private String uri;

    private List<String> isbn13;
}
