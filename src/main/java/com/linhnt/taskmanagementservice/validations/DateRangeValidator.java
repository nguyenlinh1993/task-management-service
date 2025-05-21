package com.linhnt.taskmanagementservice.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = DateRangeValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@ReportAsSingleViolation
public @interface DateRangeValidator {
    String fromDate();

    String toDate();

    String format();

    String message() default "The end date must be greater than the start date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
