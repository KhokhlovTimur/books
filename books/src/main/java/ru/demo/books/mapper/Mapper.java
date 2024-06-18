package ru.demo.books.mapper;

import ru.demo.books.dto.author.AuthorResponse;
import ru.demo.books.dto.author.CreateAuthorRequest;
import ru.demo.books.dto.book.BookResponse;
import ru.demo.books.dto.book.CreateBookRequest;
import ru.demo.books.model.Author;
import ru.demo.books.model.Book;

public interface Mapper {

    Author toModel(CreateAuthorRequest req);

    AuthorResponse toResponse(Author author);

    AuthorResponse toResponseInBook(Author author);

    BookResponse toResponseInAuthor(Book book);

    BookResponse toResponse(Book book);

    Book toModel(CreateBookRequest req);

}
