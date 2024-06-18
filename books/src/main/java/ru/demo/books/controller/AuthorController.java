package ru.demo.books.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.demo.books.dto.author.AuthorResponse;
import ru.demo.books.dto.author.CreateAuthorRequest;
import ru.demo.books.service.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/author")
    public ResponseEntity<AuthorResponse> saveAuthor(@RequestBody CreateAuthorRequest author) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorService.save(author));
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorResponse>> getAuthors() {
        return ResponseEntity.ok(
                authorService.findAll()
        );
    }

    @PatchMapping("/author/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable("id") Long id,
                                                       @RequestBody CreateAuthorRequest updateAuthor) {
        return ResponseEntity.accepted()
                .body(authorService.update(id, updateAuthor));
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
