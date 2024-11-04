package ru.iteco.accountbank.service;

import ru.iteco.accountbank.model.BankBook;

import java.util.List;

public interface BankBookService {
    List<BankBook> getBankBooksById(Integer id);
}
