package ru.demo.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.books.dto.author.AuthorResponse;
import ru.demo.books.dto.author.CreateAuthorRequest;
import ru.demo.books.exception.NotFoundException;
import ru.demo.books.mapper.Mapper;
import ru.demo.books.model.Author;
import ru.demo.books.model.Status;
import ru.demo.books.repository.AuthorRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    public final Mapper mapper;

    @Override
    public AuthorResponse save(CreateAuthorRequest createAuthorRequest) {
        Author author = mapper.toModel(createAuthorRequest);
        author.setStatus(Status.ACTIVE);
        return mapper.toResponse(authorRepository.save(author));
    }

    @Override
    public Author findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Author with id [%s] not found", id)));

        if (author.getStatus().equals(Status.DELETED)) {
            throw new NotFoundException(String.format("Author with id [%s] not found", id));
        }

        return author;
    }

    @Override
    @Transactional
    public AuthorResponse update(Long id, CreateAuthorRequest updateAuthorRequest) {
        Author author = findById(id);

        if (updateAuthorRequest.getAge() != null) {
            author.setAge(updateAuthorRequest.getAge());
        }

        if (updateAuthorRequest.getName() != null) {
            author.setName(updateAuthorRequest.getName());
        }

        if (updateAuthorRequest.getSurname() != null) {
            author.setSurname(updateAuthorRequest.getSurname());
        }

        return mapper.toResponse(authorRepository.save(author));
    }

    @Override
    public List<AuthorResponse> findAll() {
        return authorRepository.findAll()
                .stream()
                .filter(author -> !author.getStatus().equals(Status.DELETED))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        Author author = findById(id);

        author.getBooks().forEach(book -> book.setStatus(Status.DELETED));
        author.setStatus(Status.DELETED);
        authorRepository.save(author);
    }

}
