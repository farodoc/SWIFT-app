package com.swift.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CountryISO2Validator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CountryISO2Constraint {

    String message() default "Invalid ISO2 code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
