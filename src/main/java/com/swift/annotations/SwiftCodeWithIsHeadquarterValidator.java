package com.swift.annotations;

import com.swift.dto.SwiftCodeEntryRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.swift.config.Config.BANK_HQ_SUFFIX;

public class SwiftCodeWithIsHeadquarterValidator implements ConstraintValidator<SwiftCodeWithIsHeadquarterConstraint, SwiftCodeEntryRequest> {
    @Override
    public void initialize(SwiftCodeWithIsHeadquarterConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SwiftCodeEntryRequest request, ConstraintValidatorContext context) {
        if (request.getIsHeadquarter() != null) {
            if (request.getIsHeadquarter() && !request.getSwiftCode().endsWith(BANK_HQ_SUFFIX)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("SWIFT code must end with 'XXX' for headquarters")
                        .addPropertyNode("swiftCode")
                        .addConstraintViolation();
                return false;
            } else if (!request.getIsHeadquarter() && request.getSwiftCode().endsWith(BANK_HQ_SUFFIX)) {
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
