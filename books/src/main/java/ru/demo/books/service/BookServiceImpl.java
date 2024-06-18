package ru.demo.books.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.books.dto.book.BookResponse;
import ru.demo.books.dto.book.CreateBookRequest;
import ru.demo.books.dto.book.SearchBooksRequest;
import ru.demo.books.exception.NotFoundException;
import ru.demo.books.mapper.Mapper;
import ru.demo.books.model.Book;
import ru.demo.books.model.Status;
import ru.demo.books.repository.AuthorRepository;
import ru.demo.books.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final Mapper mapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public BookResponse save(CreateBookRequest createBookRequest) {
        Book book = mapper.toModel(createBookRequest);
        book.setAuthor(authorService.findById(createBookRequest.getAuthorId()));
        book.setStatus(Status.ACTIVE);

        return mapper.toResponse(bookRepository.save(book));
    }

    @Override
    public List<BookResponse> findAllFiltered(SearchBooksRequest searchRequest) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> q = cb.createQuery(Book.class);
        Root<Book> root = q.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchRequest.getTitle() != null) {
            predicates.add(cb.equal(root.get("title"), searchRequest.getTitle()));
        }

        if (searchRequest.getAuthorId() != null) {
            predicates.add(cb.equal(root.get("author"), authorService.findById(searchRequest.getAuthorId())));
        }

        if (searchRequest.getIsbn() != null) {
            predicates.add(cb.equal(root.get("isbn"), searchRequest.getIsbn()));
        }

        Predicate combined = cb.and(predicates.toArray(new Predicate[predicates.size() - 1]));
        q = q.where(combined);

        return em.createQuery(q).getResultList()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public BookResponse update(Long id, CreateBookRequest createBookRequest) {
        Book book = findById(id);

        if (createBookRequest.getTitle() != null) {
            book.setTitle(createBookRequest.getTitle());
        }

        if (createBookRequest.getAuthorId() != null) {
            book.setAuthor(authorService.findById(createBookRequest.getAuthorId()));
        }

        if (createBookRequest.getIsbn() != null) {
            book.setIsbn(createBookRequest.getIsbn());
        }

        return mapper.toResponse(
                bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Book book = findById(id);
        book.getAuthor().getBooks().remove(book);

        book.setStatus(Status.DELETED);
        bookRepository.save(book);
    }

    @Override
    public Book findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id [%s] not found", id)));

        if (book.getStatus().equals(Status.DELETED)) {
            throw new NotFoundException(String.format("Book with id [%s] not found", id));
        }

        return book;
    }

}
