package com.swift.annotations;

import com.swift.dto.SwiftCodeEntryRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SwiftCodeWithIsHeadquarterValidator implements ConstraintValidator<SwiftCodeWithIsHeadquarterConstraint, SwiftCodeEntryRequest> {

    private static final String HQ_SUFFIX = "XXX";

    @Override
    public void initialize(SwiftCodeWithIsHeadquarterConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SwiftCodeEntryRequest request, ConstraintValidatorContext context) {
        if (request.getIsHeadquarter() != null) {
            if (request.getIsHeadquarter() && !request.getSwiftCode().endsWith(HQ_SUFFIX)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("SWIFT code must end with 'XXX' for headquarters")
                        .addPropertyNode("swiftCode")
                        .addConstraintViolation();
                return false;
            } else if (!request.getIsHeadquarter() && request.getSwiftCode().endsWith(HQ_SUFFIX)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("SWIFT code must not end with 'XXX' for branches")
                        .addPropertyNode("swiftCode")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }

}
