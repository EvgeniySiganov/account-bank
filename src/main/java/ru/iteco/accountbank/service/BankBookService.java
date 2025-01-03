package ru.iteco.accountbank.service;

import ru.iteco.accountbank.model.BankBookDto;

import java.util.List;

public interface BankBookService {
    List<BankBookDto> getAllBankBookDtoByUserId(Integer userid);
    BankBookDto getBankBookById(Integer bankBookId);
    BankBookDto post(BankBookDto bankBookDto);
    BankBookDto put(BankBookDto bankBookDto);
    void deleteBankBook(Integer bankBookId);
    void deleteAllBankBookByUserId(Integer userId);
}
