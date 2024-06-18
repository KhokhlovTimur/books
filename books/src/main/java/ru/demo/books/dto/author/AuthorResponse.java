package ru.demo.books.dto.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.demo.books.dto.book.BookResponse;
import ru.demo.books.model.Author;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorResponse {

    private Long id;

    private String name;

    private String surname;

    private int age;

    private Set<BookResponse> books;

}
