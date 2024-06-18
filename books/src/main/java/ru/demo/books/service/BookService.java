package ru.demo.books.service;

import ru.demo.books.dto.book.BookResponse;
import ru.demo.books.dto.book.CreateBookRequest;
import ru.demo.books.dto.book.SearchBooksRequest;
import ru.demo.books.model.Book;

import java.util.List;

public interface BookService {

    BookResponse save(CreateBookRequest createBookRequest);

    List<BookResponse> findAllFiltered(SearchBooksRequest searchRequest);

    BookResponse update(Long id, CreateBookRequest createBookRequest);

    void deleteById(Long id);

    Book findById(Long id);

}
