package com.igor.library.controller;

import com.igor.library.exception.EntityInvalid;
import com.igor.library.model.request.BookRequestDTO;
import com.igor.library.model.response.BookResponseDTO;
import com.igor.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("book")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "999") int size,
                                                        @RequestParam(required = false, defaultValue = "id") String sort) {
        return new ResponseEntity<>(bookService.getAll(page, size, sort), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(bookService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Page<BookResponseDTO>> getAllByAuthor(@RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "999") int size,
                                                        @RequestParam(required = false, defaultValue = "id") String sort,
                                                                @PathVariable("id") Long id) {
        return new ResponseEntity<>(bookService.getAllByAuthor(page, size, sort, id), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Page<BookResponseDTO>> getAllByCategory(@RequestParam(required = false, defaultValue = "0") int page,
                                                                @RequestParam(required = false, defaultValue = "999") int size,
                                                                @RequestParam(required = false, defaultValue = "id") String sort,
                                                                @PathVariable("id") Long id) {
        return new ResponseEntity<>(bookService.getAllByCategory(page, size, sort, id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO book, Errors errors) {
        if (errors.hasErrors()) {
            throw new EntityInvalid("Invalid information, please fill in all fields");
        }

        return new ResponseEntity<>(bookService.create(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO book, Errors errors, @PathVariable("id") Long id) {
        if (errors.hasErrors()) {
            throw new EntityInvalid("Invalid information, please fill in all fields");
        }

        return new ResponseEntity<>(bookService.update(book, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
