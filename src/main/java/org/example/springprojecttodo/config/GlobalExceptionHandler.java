package org.example.springprojecttodo.config;

import lombok.extern.slf4j.Slf4j;
import org.example.springprojecttodo.exeption.EntityNotFound;
import org.example.springprojecttodo.exeption.IllegalUserInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFound.class)
    public void handleEntityNotFound(EntityNotFound e) {
        log.error("Entity not found: {}", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(IllegalUserInputException.class)
    public ResponseEntity<Object> handleIllegalUserInputException(IllegalUserInputException e) {
        log.error("Illegal user input: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public void handleAllExceptions(Throwable e) {
        log.error("Internal server error: {}", e.getMessage());
        e.printStackTrace();
    }
}
