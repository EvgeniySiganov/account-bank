package ru.iteco.accountbank.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = CurrencyValidator.class)
public @interface Currency {
    String message() default "Invalid currency";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
