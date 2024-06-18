package ru.demo.books.service;

import ru.demo.books.dto.author.AuthorResponse;
import ru.demo.books.dto.author.CreateAuthorRequest;
import ru.demo.books.model.Author;

import java.util.List;

public interface AuthorService {

    AuthorResponse save(CreateAuthorRequest createAuthorRequest);

    Author findById(Long id);

    AuthorResponse update(Long id, CreateAuthorRequest updateAuthorRequest);

    List<AuthorResponse> findAll();

    void deleteById(Long id);

}
