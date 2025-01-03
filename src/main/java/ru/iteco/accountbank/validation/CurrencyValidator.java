package ru.iteco.accountbank.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import ru.iteco.accountbank.repository.CurrencyRepository;


@Slf4j
public class CurrencyValidator implements ConstraintValidator<Currency, String> {
    private final CurrencyRepository currencyRepository;

    public CurrencyValidator(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //Optional<CurrencyEntity> currencyEntity = currencyRepository.findByName(s);
        boolean b = currencyRepository.existsByName(s);
        log.info("Currency with name {} was found {}", s, b);
        return b;
    }
}

