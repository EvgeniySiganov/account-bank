package ru.iteco.accountbank.service;

import org.springframework.stereotype.Service;
import ru.iteco.accountbank.exception.BankBookAlreadyExistsException;
import ru.iteco.accountbank.exception.ChangeBankBookNumberException;
import ru.iteco.accountbank.model.BankBookDto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BankBookServiceImpl implements BankBookService {
    private final Map<Integer, BankBookDto> bankBookDtoMap = new ConcurrentHashMap<>();
    AtomicInteger atomicIntegerId = new AtomicInteger(1);


    @Override
    public List<BankBookDto> getAllBankBookDtoByUserId(Integer userid) {
        return bankBookDtoMap.values().stream().
                filter(bankBookDto -> bankBookDto.getUserId().equals(userid)).
                collect(Collectors.toList());
    }

    @Override
    public BankBookDto getBankBookById(Integer bankBookId) {
        return bankBookDtoMap.get(bankBookId);
    }

    @Override
    public BankBookDto post(BankBookDto bankBookDto) {
        for (BankBookDto value : bankBookDtoMap.values()) {
            if (value.getUserId().equals(bankBookDto.getUserId())
                    && value.getNumber().equals(bankBookDto.getNumber())
                    && value.getCurrency().equals(bankBookDto.getCurrency())) {
                throw new BankBookAlreadyExistsException("The bank book already exists in system, id: " + value.getId());
            }
        }
        Integer id = atomicIntegerId.getAndIncrement();
        bankBookDto.setId(id);
        bankBookDtoMap.put(id, bankBookDto);
        return bankBookDto;
    }

    @Override
    public BankBookDto put(BankBookDto bankBookDto) {
        if (!bankBookDtoMap.get(bankBookDto.getId()).getNumber().equals(bankBookDto.getNumber())) {
            throw new ChangeBankBookNumberException("It's restrict to change number of bank book. Number: " + bankBookDto.getNumber());
        }
        bankBookDtoMap.put(bankBookDto.getId(), bankBookDto);
        return bankBookDto;
    }

    @Override
    public void deleteBankBook(Integer bankBookId) {
        bankBookDtoMap.remove(bankBookId);
    }

    @Override
    public void deleteAllBankBookByUserId(Integer userId) {
        List<Integer> list = bankBookDtoMap.values().stream()
                .filter(bankBookDto -> bankBookDto.getUserId().equals(userId))
                .map(BankBookDto::getId)
                .toList();

        for (Integer value : list) {
            bankBookDtoMap.remove(value);

        }
    }
}
