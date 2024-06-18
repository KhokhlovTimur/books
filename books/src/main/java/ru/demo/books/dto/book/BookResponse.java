package ru.demo.books.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.demo.books.dto.author.AuthorResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse {

    private Long id;

    private String title;

    private AuthorResponse authorResponse;

    private String isbn;

}
