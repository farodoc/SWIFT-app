package com.swift.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SwiftCodeValidator implements ConstraintValidator<SwiftCodeConstraint, String> {

    @Override
    public void initialize(SwiftCodeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Invalid SWIFT code").addConstraintViolation().disableDefaultConstraintViolation();
        return value.matches("^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?$");
    }

}
