package ru.iteco.accountbank.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.iteco.accountbank.model.AccountInfo;
import ru.iteco.accountbank.model.BankBook;
import ru.iteco.accountbank.model.PersonalInfo;

import java.util.List;
import java.util.Map;

@Service
@Primary
public class AccountServiceImpl implements AccountService{
    private final PersonaInformationService personaInformationService;
    private final Map<String, BankBookService> bankBookServices;

    public AccountServiceImpl(@Lazy PersonaInformationService personaInformationService,
                              Map<String, BankBookService> bankBookServices) {
        this.personaInformationService = personaInformationService;
        this.bankBookServices = bankBookServices;
    }

    public AccountInfo getAccountInfoById(Integer id){
        AccountInfo accountInfo = new AccountInfo();
        PersonalInfo personalInfo = personaInformationService.getPersonalInfoById(id);
        accountInfo.setPersonalInfo(personalInfo);
        System.out.println(bankBookServices);
        for (Map.Entry<String, BankBookService> bankBookServiceEntry : bankBookServices.entrySet()) {
            BankBookService bankBookService = bankBookServiceEntry.getValue();
            List<BankBook> bankBooks = bankBookService.getBankBooksById(id);
            System.out.println(bankBooks);
            if(!bankBooks.isEmpty()){
                accountInfo.setBankBooks(bankBooks);
            }
        }
        return accountInfo;
    }

    @Override
    public String getPersonalInfoClass() {
        return personaInformationService.getClass().toString();
    }
}
