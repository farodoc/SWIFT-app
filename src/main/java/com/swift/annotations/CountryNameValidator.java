package com.swift.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryNameValidator implements ConstraintValidator<CountryNameConstraint, String> {

    @Override
    public void initialize(CountryNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Invalid country name: " + value).addConstraintViolation().disableDefaultConstraintViolation();
        return value.matches("^[A-Za-z ]+$");
    }

}
