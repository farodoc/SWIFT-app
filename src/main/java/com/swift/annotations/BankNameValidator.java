package com.swift.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BankNameValidator implements ConstraintValidator<BankNameConstraint, String> {

    @Override
    public void initialize(BankNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Invalid bank name: " + value).addConstraintViolation().disableDefaultConstraintViolation();
        return value.matches("^[A-Za-z0-9 ]+$");
    }

}
