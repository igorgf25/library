package com.igor.library.service;

import com.igor.library.model.request.AuthorRequestDTO;
import com.igor.library.model.response.AuthorResponseDTO;
import org.springframework.data.domain.Page;

public interface AuthorService {
    Page<AuthorResponseDTO> getAll(int page, int size, String sort);

    AuthorResponseDTO getById(Long id);

    AuthorResponseDTO create(AuthorRequestDTO author);

    AuthorResponseDTO update(Long id, AuthorRequestDTO author);

    void delete(Long id);
}
