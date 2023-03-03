package com.igor.library.service;

import com.igor.library.model.request.BookRequestDTO;
import com.igor.library.model.response.BookResponseDTO;
import org.springframework.data.domain.Page;

public interface BookService {
    Page<BookResponseDTO> getAll(int page, int size, String sort);

    BookResponseDTO getById(Long id);

    BookResponseDTO create(BookRequestDTO bookRequest);

    BookResponseDTO update(BookRequestDTO bookRequest, Long id);

    void delete(Long id);

    Page<BookResponseDTO> getAllByAuthor(int page, int size, String sort, Long id);

    Page<BookResponseDTO> getAllByCategory(int page, int size, String sort, Long id);
}
