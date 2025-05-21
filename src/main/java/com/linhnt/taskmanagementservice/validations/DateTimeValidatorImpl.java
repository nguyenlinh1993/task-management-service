package com.linhnt.taskmanagementservice.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateTimeValidatorImpl implements ConstraintValidator<DateTimeValidator, String> {
    private DateFormat dateFormat;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            dateFormat.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(DateTimeValidator constraintAnnotation) {
        dateFormat = new SimpleDateFormat(constraintAnnotation.pattern(), Locale.getDefault());
        dateFormat.setLenient(false);
    }
}
