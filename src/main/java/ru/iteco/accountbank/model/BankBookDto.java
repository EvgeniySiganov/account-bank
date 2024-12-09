package ru.iteco.accountbank.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class BankBookDto {
    private Integer id;

    private Integer userId;

    private String number;

    private BigDecimal amount;

    private String currency;
}
