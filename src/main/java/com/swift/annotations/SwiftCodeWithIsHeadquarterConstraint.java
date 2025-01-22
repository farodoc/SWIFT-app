package com.swift.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SwiftCodeWithIsHeadquarterValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwiftCodeWithIsHeadquarterConstraint {
    String message() default "Invalid SWIFT code for the given headquarter status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
