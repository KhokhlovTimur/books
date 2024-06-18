package ru.demo.books.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchBooksRequest {

    private String title;

    private String isbn;

    private Long authorId;

}
