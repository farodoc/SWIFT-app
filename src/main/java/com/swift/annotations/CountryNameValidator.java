package com.swift.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryNameValidator implements ConstraintValidator<CountryNameConstraint, String> {
    private final static String COUNTRY_NAME_PATTERN = "^[A-Za-z ]+$";

    @Override
    public void initialize(CountryNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Invalid country name: " + value).addConstraintViolation().disableDefaultConstraintViolation();
        if (value == null) {
            return false;
        }
        return value.matches(COUNTRY_NAME_PATTERN);
    }
}
