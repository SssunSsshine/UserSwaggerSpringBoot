package com.vsu.app.exception;

import org.apache.coyote.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest()

                .body(Map.of("errors", errors));
    }

    @ExceptionHandler(DBException.class)
    ResponseEntity<Object> handleDBException(DBException e) {

        return ResponseEntity.badRequest()

                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<Object> handleValidationException(ValidationException e) {

        return ResponseEntity.badRequest()

                .body(Map.of("error", e.getMessage()));
    }
}
