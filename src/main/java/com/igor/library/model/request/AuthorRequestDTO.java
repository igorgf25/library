package com.igor.library.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class AuthorRequestDTO {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String nationality;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate birthDate;
}
