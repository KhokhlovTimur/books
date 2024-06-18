package ru.demo.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.demo.books.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
