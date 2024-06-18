package ru.demo.books.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.demo.books.dto.ExceptionDto;
import ru.demo.books.exception.NotFoundException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException e) {
        return createResponse(HttpStatus.NOT_FOUND, e);
    }

    private ResponseEntity<ExceptionDto> createResponse(HttpStatus status, Exception e) {
        log.error(e.getMessage());

        return ResponseEntity.status(status)
                .body(ExceptionDto.builder()
                        .message(e.getMessage())
                        .build());
    }

}