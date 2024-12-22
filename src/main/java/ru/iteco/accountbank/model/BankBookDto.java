package ru.iteco.accountbank.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import ru.iteco.accountbank.validation.Create;
import ru.iteco.accountbank.validation.Currency;
import ru.iteco.accountbank.validation.Update;

import java.math.BigDecimal;

@Builder
@Data
public class BankBookDto {
    @Null(groups = Create.class)
    @NotBlank(groups = Update.class)
    private Integer id;

    private Integer userId;

    @NotBlank(message = "Not blank!")
    private String number;

    @Min(value = 0L)
    private BigDecimal amount;

    @Currency
    private String currency;
}
