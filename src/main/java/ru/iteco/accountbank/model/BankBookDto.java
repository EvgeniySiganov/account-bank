package ru.iteco.accountbank.model;

import jakarta.validation.constraints.*;
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
    @NotNull(groups = Update.class)
    private Integer id;

    private Integer userId;

    @NotBlank(message = "Not blank!")
    private String number;

    @DecimalMin(value = "0.0", message = "The value must be greater 0")
    private BigDecimal amount;

    @Currency
    private String currencyName;
}
