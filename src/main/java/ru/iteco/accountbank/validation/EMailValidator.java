package ru.iteco.accountbank.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EMailValidator implements ConstraintValidator<Email, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        String pattern = "\\w+@\\w+\\.\\w{2,}";
        Matcher matcher = Pattern.compile(pattern).matcher(s);
        return matcher.find();
    }
}
