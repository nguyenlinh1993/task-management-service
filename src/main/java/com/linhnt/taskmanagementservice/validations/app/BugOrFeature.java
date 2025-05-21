package com.linhnt.taskmanagementservice.validations.app;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = BugOrFeatureValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BugOrFeature {
    String message() default "30020009.400_need_to_define_either_bug_or_feature_for_task";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}