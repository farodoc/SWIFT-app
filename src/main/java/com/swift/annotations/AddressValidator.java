package com.swift.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<AddressConstraint, String> {
    private final static String ADDRESS_PATTERN = "^[A-Za-z0-9 ,.]+$";

    @Override
    public void initialize(AddressConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Invalid address: " + value).addConstraintViolation().disableDefaultConstraintViolation();
        if (value == null) {
            return false;
        }
        return value.matches(ADDRESS_PATTERN);
    }
}
