package ru.iteco.accountbank.exception;

public class CurrencyMissMatchException extends RuntimeException {
    public CurrencyMissMatchException(String message) {
        super(message);
    }
}
