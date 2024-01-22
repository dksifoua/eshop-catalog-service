package io.dksifoua.eshop.catalog.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public Mono<ResponseEntity<String>> handle(DuplicateKeyException exception) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage()));
    }
}
