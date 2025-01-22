package com.swift.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BankNameValidator implements ConstraintValidator<BankNameConstraint, String> {
    private final static String BANK_NAME_PATTERN = "^[A-Za-z0-9 ]+$";

    @Override
    public void initialize(BankNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Invalid bank name: " + value).addConstraintViolation().disableDefaultConstraintViolation();
        if (value == null) {
            return false;
        }
        return value.matches(BANK_NAME_PATTERN);
    }
}
