package ru.iteco.accountbank.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDto {
    private Integer id;

    private Integer sourceBankBookId;

    private Integer targetBankBookId;

    private BigDecimal amount;

    private LocalDateTime initiationDate;

    private LocalDateTime completionDate;

    private String statusName;

}
