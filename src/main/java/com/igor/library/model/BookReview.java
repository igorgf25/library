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
public class BookReview {

    private String status;

    private String copyright;

    @JsonProperty("num_results")
    private Integer numResults;

    private List<BookReviewResults> results;

}
