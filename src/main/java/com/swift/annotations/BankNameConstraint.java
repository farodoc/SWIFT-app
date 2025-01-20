package com.swift.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BankNameValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BankNameConstraint {

    String message() default "Invalid bank name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
