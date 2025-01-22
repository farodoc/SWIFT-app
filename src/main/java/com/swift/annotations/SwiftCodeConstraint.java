package com.swift.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SwiftCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwiftCodeConstraint {
    String message() default "Invalid SWIFT code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
