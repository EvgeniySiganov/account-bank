package ru.iteco.accountbank.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.iteco.accountbank.model.ErrorDto;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String collect = exception.getBindingResult().getAllErrors().stream()
                .map(objectError -> {
                    String field = ((FieldError) objectError).getField();
                    return field + ": " + objectError.getDefaultMessage();
                })
                .collect(Collectors.joining("; "));

        return ErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(collect)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDto handleConstraintViolation(ConstraintViolationException exception) {
        return ErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
    }
}
