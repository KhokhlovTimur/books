package ru.demo.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.demo.books.model.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    //чтобы не было n+1
    @Query("select a from Author a left join fetch a.books b")
    List<Author> findAll();

}
