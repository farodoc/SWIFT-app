package com.swift.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryISO2Validator implements ConstraintValidator<CountryISO2Constraint, String> {
    private final static String ISO2_REGEX = "^[A-Z]{2}$";

    @Override
    public void initialize(CountryISO2Constraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Invalid ISO2 code: " + value).addConstraintViolation().disableDefaultConstraintViolation();
        if (value == null) {
            return false;
        }
        return value.matches(ISO2_REGEX);
    }
}
