package ru.iteco.accountbank.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class CurrencyValidator implements ConstraintValidator<Currency, String> {
    private static final Set<String> SUPPORTED_CURRENCIES = Set.of("USD", "EUR", "GBP", "JPY");


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return SUPPORTED_CURRENCIES.contains(s);
    }
}
