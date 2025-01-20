package com.swift.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<AddressConstraint, String> {

    @Override
    public void initialize(AddressConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Invalid address: " + value).addConstraintViolation().disableDefaultConstraintViolation();
        return value.matches("^[A-Za-z0-9 ,.]+$");
    }
}
