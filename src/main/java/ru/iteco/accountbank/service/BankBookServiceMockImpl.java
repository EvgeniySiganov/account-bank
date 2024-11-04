package ru.iteco.accountbank.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.iteco.accountbank.model.BankBook;

import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
public class BankBookServiceMockImpl implements BankBookService {
    @Override
    public List<BankBook> getBankBooksById(Integer id) {
        return new ArrayList<>();
    }
}
