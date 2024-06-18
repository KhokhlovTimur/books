package ru.demo.books.mapper;

import org.springframework.stereotype.Component;
import ru.demo.books.dto.author.AuthorResponse;
import ru.demo.books.dto.author.CreateAuthorRequest;
import ru.demo.books.dto.book.BookResponse;
import ru.demo.books.dto.book.CreateBookRequest;
import ru.demo.books.model.Author;
import ru.demo.books.model.Book;
import ru.demo.books.model.Status;

import java.util.stream.Collectors;

//можно было сделать через mapstruct
@Component
public class MapperImpl implements Mapper {

    @Override
    public Author toModel(CreateAuthorRequest req) {
        return Author.builder()
                .surname(req.getSurname())
                .name(req.getName())
                .age(req.getAge())
                .build();
    }

    @Override
    public AuthorResponse toResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .surname(author.getSurname())
                .name(author.getName())
                .age(author.getAge())
                .books(author.getBooks() == null ? null :
                        author.getBooks()
                        .stream()
                        .map(this::toResponseInAuthor)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public AuthorResponse toResponseInBook(Author author) {
        return author.getStatus().equals(Status.DELETED) ? null :
                AuthorResponse.builder()
                .id(author.getId())
                .surname(author.getSurname())
                .name(author.getName())
                .age(author.getAge())
                .build();
    }

    @Override
    public BookResponse toResponse(Book book) {
        return book == null ? null :
                BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .authorResponse(toResponseInBook(book.getAuthor()))
                .build();
    }

    @Override
    public Book toModel(CreateBookRequest req) {
        return Book.builder()
                .title(req.getTitle())
                .isbn(req.getIsbn())
                .build();
    }

    @Override
    public BookResponse toResponseInAuthor(Book book) {
        return book.getStatus().equals(Status.DELETED) ? null :
                BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .build();
    }
}
