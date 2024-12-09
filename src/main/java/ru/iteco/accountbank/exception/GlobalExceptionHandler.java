package ru.iteco.accountbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.iteco.accountbank.model.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDto> handleMissingPathVariable(NoHandlerFoundException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .message(ErrorDto.PARAMETER_NOT_PASSED)
                .code(400)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(BankBookNotFoundException.class)
    public ResponseEntity<ErrorDto> handleBankBookNotFound(BankBookNotFoundException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .message(ErrorDto.NOT_FOUND)
                .code(400)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(BankBookAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleBankBookAlreadyExists(BankBookAlreadyExistsException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .message(e.getMessage())
                .code(400)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(ChangeBankBookNumberException.class)
    public ResponseEntity<ErrorDto> handleBankBookAlreadyExists(ChangeBankBookNumberException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .message(e.getMessage())
                .code(400)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
}