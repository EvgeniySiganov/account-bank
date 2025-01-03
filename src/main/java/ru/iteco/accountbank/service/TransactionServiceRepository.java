package ru.iteco.accountbank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iteco.accountbank.exception.BankBookNotFoundException;
import ru.iteco.accountbank.exception.CurrencyMissMatchException;
import ru.iteco.accountbank.exception.NegativeAmountException;
import ru.iteco.accountbank.model.TransactionDto;
import ru.iteco.accountbank.model.entity.TransactionEntity;
import ru.iteco.accountbank.repository.BankBookRepository;
import ru.iteco.accountbank.repository.StatusRepository;
import ru.iteco.accountbank.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TransactionServiceRepository implements TransactionService {
    private final BankBookRepository bankBookRepository;
    private final TransactionRepository transactionRepository;
    private final StatusRepository statusRepository;

    @Override
    public TransactionDto send(TransactionDto transactionDto) {
        TransactionEntity transactionEntity = sendWithProcessingStatus(transactionDto);
        if (transactionEntity.getSourceBankBook().getCurrency().equals(transactionEntity.getTargetBankBook().getCurrency())) {
            BigDecimal subtract = transactionEntity.getSourceBankBook().getAmount().subtract(transactionEntity.getAmount());
            if (subtract.compareTo(BigDecimal.ZERO) >= 0) {
                transactionEntity.getSourceBankBook().setAmount(subtract);
                transactionEntity.getTargetBankBook().setAmount(transactionEntity.getTargetBankBook().getAmount().add(transactionEntity.getAmount()));
                transactionEntity.setStatus(statusRepository.findByName("successful"));
                transactionEntity.setCompletionDate(LocalDateTime.now());
                return mapToDto(transactionRepository.save(transactionEntity));
            } else {
                throw new NegativeAmountException();
            }
        } else {
            throw new CurrencyMissMatchException("It's not possible to send transaction where sender currency is "
                    + transactionEntity.getSourceBankBook().getCurrency() + " and receiver currency is "
                    + transactionEntity.getTargetBankBook().getCurrency());
        }
    }

    private TransactionEntity sendWithProcessingStatus(TransactionDto transactionDto) {
        TransactionEntity processing = TransactionEntity.builder()
                .amount(transactionDto.getAmount())
                .status(statusRepository.findByName("processing"))
                .initiationDate(transactionDto.getInitiationDate())
                .sourceBankBook(bankBookRepository.findById(transactionDto.getSourceBankBookId())
                        .orElseThrow(BankBookNotFoundException::new))
                .targetBankBook(bankBookRepository.findById(transactionDto.getTargetBankBookId())
                        .orElseThrow(BankBookNotFoundException::new))
                .build();
        return transactionRepository.save(processing);
    }

    private static TransactionDto mapToDto(TransactionEntity transactionEntity) {
        return TransactionDto.builder()
                .amount(transactionEntity.getAmount())
                .id(transactionEntity.getId())
                .completionDate(transactionEntity.getCompletionDate())
                .initiationDate(transactionEntity.getInitiationDate())
                .sourceBankBookId(transactionEntity.getSourceBankBook().getId())
                .targetBankBookId(transactionEntity.getTargetBankBook().getId())
                .statusName(transactionEntity.getStatus().getName())
                .build();
    }
}