package com.igor.library.service.impl;

import com.igor.library.exception.EntityAlreadyExist;
import com.igor.library.exception.EntityNotFound;
import com.igor.library.model.Author;
import com.igor.library.model.request.AuthorRequestDTO;
import com.igor.library.model.response.AuthorResponseDTO;
import com.igor.library.repository.AuthorRepository;
import com.igor.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    private final ModelMapper mapper;

    @Override
    public Page<AuthorResponseDTO> getAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        Page<Author> resultPage = repository.findAll(pageable);
        return resultPage.map(obj -> mapper.map(obj, AuthorResponseDTO.class));
    }

    @Override
    public AuthorResponseDTO getById(Long id) {
        Author author = repository.findById(id).orElseThrow(() -> new EntityNotFound("Autor com o id " + id + " não encontrada."));
        return mapper.map(author, AuthorResponseDTO.class);
    }

    @Override
    public AuthorResponseDTO create(AuthorRequestDTO author) {
        Optional<Author> alreadyExist = repository.findAuthorByBirthDataAndName(author.getBirthDate(), author.getName());

        if (alreadyExist.isPresent()) {
            throw new EntityAlreadyExist("Author já registrada no banco de dados.");
        }

        Author authorResponse = repository.save(mapper.map(author, Author.class));
        return mapper.map(authorResponse, AuthorResponseDTO.class);
    }

    @Override
    public AuthorResponseDTO update(Long id, AuthorRequestDTO author) {
        Optional<Author> authorVerification = repository.findById(id);

        if (authorVerification.isEmpty()) {
            throw new EntityNotFound("Author não existe no banco de dados.");
        }

        author.setId(id);
        Author authorResponse = repository.save(mapper.map(author, Author.class));
        return mapper.map(authorResponse, AuthorResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        Optional<Author> authorVerification = repository.findById(id);
        if (authorVerification.isEmpty()) {
            throw new EntityNotFound("Author não existe no banco de dados.");
        }

        repository.deleteById(id);
    }
}
