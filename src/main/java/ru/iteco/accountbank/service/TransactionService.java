package ru.iteco.accountbank.service;

import ru.iteco.accountbank.model.TransactionDto;

public interface TransactionService {
    TransactionDto send(TransactionDto transactionDto);
}
