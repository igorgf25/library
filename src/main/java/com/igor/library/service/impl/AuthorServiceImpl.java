package com.igor.library.service.impl;

import com.igor.library.exception.EntityAlreadyExist;
import com.igor.library.exception.EntityNotFound;
import com.igor.library.model.Author;
import com.igor.library.model.request.AuthorRequestDTO;
import com.igor.library.model.response.AuthorResponseDTO;
import com.igor.library.repository.AuthorRepository;
import com.igor.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    private final ModelMapper mapper;

    @Override
    public Page<AuthorResponseDTO> getAll(int page, int size, String sort) {
        log.info("AuthorServiceImpl.getAll getting all the authors in the database");
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        Page<Author> resultPage = repository.findAll(pageable);
        return resultPage.map(obj -> mapper.map(obj, AuthorResponseDTO.class));
    }

    @Override
    public AuthorResponseDTO getById(Long id) {
        log.info("AuthorServiceImpl.getById getting the author with id " + id + ".");
        Author author = repository.findById(id).orElseThrow(() -> new EntityNotFound("Author with id " + id + " not found."));
        return mapper.map(author, AuthorResponseDTO.class);
    }

    @Override
    public AuthorResponseDTO create(AuthorRequestDTO author) {
        log.info("AuthorServiceImpl.create inserting an author in the database");
        Optional<Author> alreadyExist = repository.findAuthorByBirthDataAndName(author.getBirthDate(), author.getName());

        if (alreadyExist.isPresent()) {
            throw new EntityAlreadyExist("Author already exists in the database.");
        }

        Author authorResponse = repository.save(mapper.map(author, Author.class));
        return mapper.map(authorResponse, AuthorResponseDTO.class);
    }

    @Override
    public AuthorResponseDTO update(Long id, AuthorRequestDTO author) {
        log.info("AuthorServiceImpl.update updating the author with id " + id +".");
        Optional<Author> authorVerification = repository.findById(id);

        if (authorVerification.isEmpty()) {
            throw new EntityNotFound("Author not found.");
        }

        author.setId(id);
        Author authorResponse = repository.save(mapper.map(author, Author.class));
        return mapper.map(authorResponse, AuthorResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        log.info("AuthorServiceImpl.delete deleting author with id " + id + ".");
        Optional<Author> authorVerification = repository.findById(id);
        if (authorVerification.isEmpty()) {
            throw new EntityNotFound("Author not found.");
        }

        repository.deleteById(id);
    }
}
