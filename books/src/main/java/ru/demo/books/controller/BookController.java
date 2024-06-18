package ru.demo.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.demo.books.dto.book.BookResponse;
import ru.demo.books.dto.book.CreateBookRequest;
import ru.demo.books.dto.book.SearchBooksRequest;
import ru.demo.books.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<BookResponse> save(@RequestBody CreateBookRequest createBookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.save(createBookRequest));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/book/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable("id") Long id,
                                               @RequestBody CreateBookRequest updateBookRequest) {

        return ResponseEntity.accepted()
                .body(bookService.update(id, updateBookRequest));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> findAllFiltered(@ModelAttribute SearchBooksRequest searchBooksRequest) {
        return ResponseEntity.ok(bookService.findAllFiltered(searchBooksRequest));
    }
}
