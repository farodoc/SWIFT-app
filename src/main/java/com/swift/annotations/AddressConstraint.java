package com.swift.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AddressValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AddressConstraint {
    String message() default "Invalid address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
