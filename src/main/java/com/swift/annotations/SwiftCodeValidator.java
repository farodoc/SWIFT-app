package com.swift.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SwiftCodeValidator implements ConstraintValidator<SwiftCodeConstraint, String> {
    private final static String SWIFT_CODE_REGEX = "^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?$";

    @Override
    public void initialize(SwiftCodeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Invalid SWIFT code: " + value).addConstraintViolation().disableDefaultConstraintViolation();
        if (value == null) {
            return false;
        }

        return value.matches(SWIFT_CODE_REGEX);
    }
}
