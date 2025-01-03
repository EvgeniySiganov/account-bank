package ru.iteco.accountbank.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.exception.BankBookAlreadyExistsException;
import ru.iteco.accountbank.exception.ChangeBankBookNumberException;
import ru.iteco.accountbank.model.BankBookDto;
import ru.iteco.accountbank.model.entity.BankBookEntity;
import ru.iteco.accountbank.model.entity.CurrencyEntity;
import ru.iteco.accountbank.model.entity.UserEntity;
import ru.iteco.accountbank.repository.BankBookRepository;
import ru.iteco.accountbank.repository.CurrencyRepository;
import ru.iteco.accountbank.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
@RequiredArgsConstructor
public class BankBookServiceRepository implements BankBookService {

    private final BankBookRepository bankBookRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    @Override
    public List<BankBookDto> getAllBankBookDtoByUserId(Integer userid) {
        return bankBookRepository.findBankBookEntityByUserId(userid).stream()
                .map(BankBookServiceRepository::toBankBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BankBookDto getBankBookById(Integer bankBookId) {
        return toBankBookDto(bankBookRepository.findBankBookEntityById(bankBookId));
    }

    @Override
    public BankBookDto post(BankBookDto bankBookDto) {
        CurrencyEntity currencyEntityByName = currencyRepository.findCurrencyEntityByName(bankBookDto.getCurrencyName());
        List<BankBookEntity> bankBookEntityByUserIdAndNumberAndCurrency = bankBookRepository
                .findBankBookEntityByUserIdAndNumberAndCurrencyId(
                        bankBookDto.getUserId(),
                        bankBookDto.getNumber(),
                        currencyEntityByName.getId());
        if (bankBookEntityByUserIdAndNumberAndCurrency.isEmpty()) {
            BankBookEntity bankBookEntity = toBankBookEntityWithoutUserAndCurrency(bankBookDto);
            UserEntity userEntityById = userRepository.getUserEntityById(bankBookDto.getUserId());
            bankBookEntity.setUser(userEntityById);
            bankBookEntity.setCurrency(currencyEntityByName);
            return toBankBookDto(bankBookRepository.save(bankBookEntity));
        } else {
            throw new BankBookAlreadyExistsException("The bank book already exists\n" + bankBookDto);
        }
    }

    @Override
    public BankBookDto put(BankBookDto bankBookDto) {
        BankBookEntity bankBookEntityById = bankBookRepository.findBankBookEntityById(bankBookDto.getId());
        if (bankBookEntityById == null || !bankBookEntityById.getNumber().equals(bankBookDto.getNumber())) {
            throw new ChangeBankBookNumberException("It's restrict to change number of bank book. Number: " + bankBookDto.getNumber());
        } else {
            bankBookEntityById.setAmount(bankBookDto.getAmount());
            bankBookEntityById.setId(bankBookDto.getId());
            UserEntity userEntityById = userRepository.getUserEntityById(bankBookDto.getUserId());
            bankBookEntityById.setUser(userEntityById);
            CurrencyEntity currencyEntityById = currencyRepository.findCurrencyEntityByName(bankBookDto.getCurrencyName());
            bankBookEntityById.setCurrency(currencyEntityById);
            log.info("Before update bank book: " + bankBookEntityById);
            log.info("User id: " + bankBookEntityById.getUser().getId());
            return toBankBookDto(bankBookRepository.save(bankBookEntityById));
        }
    }

    @Override
    public void deleteBankBook(Integer bankBookId) {
        bankBookRepository.deleteById(bankBookId);
    }

    @Override
    @Transactional
    public void deleteAllBankBookByUserId(Integer userId) {
        bankBookRepository.deleteAllByUserId(userId);
    }

    private static BankBookDto toBankBookDto(BankBookEntity bankBookEntity) {
        return BankBookDto.builder()
                .id(bankBookEntity.getId())
                .amount(bankBookEntity.getAmount())
                .number(bankBookEntity.getNumber())
                .currencyName(bankBookEntity.getCurrency().getName())
                .userId(bankBookEntity.getUser().getId())
                .build();
    }

    private static BankBookEntity toBankBookEntityWithoutUserAndCurrency(BankBookDto bankBookDto) {
        BankBookEntity bankBookEntity = new BankBookEntity();
        bankBookEntity.setId(bankBookDto.getId());
        bankBookEntity.setAmount(bankBookDto.getAmount());
        bankBookEntity.setNumber(bankBookDto.getNumber());
        //bankBookEntity.setCurrency(bankBookDto.getCurrency_id());
        return bankBookEntity;
    }
}

