package com.igor.library.controller;

import com.igor.library.exception.EntityInvalid;
import com.igor.library.model.request.AuthorRequestDTO;
import com.igor.library.model.response.AuthorResponseDTO;
import com.igor.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;


import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("author")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<Page<AuthorResponseDTO>> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "999") int size,
                                                          @RequestParam(required = false, defaultValue = "id") String sort) {
        return new ResponseEntity<>(authorService.getAll(page, size, sort), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(authorService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO author, Errors errors) {
        if (errors.hasErrors()) {
            throw new EntityInvalid("Informações inválidas, por favor preencha todos os campos");
        }

        return new ResponseEntity<>(authorService.create(author), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> update(@Valid @RequestBody AuthorRequestDTO author, Errors errors, @PathVariable("id") Long id) {
        if (errors.hasErrors()) {
            throw new EntityInvalid("Informações inválidas, por favor preencha todos os campos");
        }

        return new ResponseEntity<>(authorService.update(id, author), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
