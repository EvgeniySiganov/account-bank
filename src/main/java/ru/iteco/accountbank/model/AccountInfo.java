package ru.iteco.accountbank.model;

import lombok.Data;

import java.util.List;

@Data
public class AccountInfo {
    private PersonalInfo personalInfo;
    private List<BankBook> bankBooks;
}
