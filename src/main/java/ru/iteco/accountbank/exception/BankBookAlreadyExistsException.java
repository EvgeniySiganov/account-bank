package ru.iteco.accountbank.exception;

public class BankBookAlreadyExistsException extends RuntimeException {
    public BankBookAlreadyExistsException(String message) {
        super(message);
    }

}
