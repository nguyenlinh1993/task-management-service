package com.linhnt.taskmanagementservice.validations;

import com.linhnt.taskmanagementservice.utils.DateUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@SuppressWarnings("PMD.AvoidAccessibilityAlteration")
public class DateRangeValidatorImpl implements ConstraintValidator<DateRangeValidator, Object> {
    private String fromDate;
    private String toDate;
    private String format;

    @Override
    public void initialize(DateRangeValidator constraintAnnotation) {
        this.fromDate = constraintAnnotation.fromDate();
        this.toDate = constraintAnnotation.toDate();
        this.format = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Field fromField = object.getClass().getDeclaredField(fromDate);
            Field toField = object.getClass().getDeclaredField(toDate);
            fromField.setAccessible(true);
            toField.setAccessible(true);

            String fromFieldValue = (String) fromField.get(object);
            String toFieldValue = (String) toField.get(object);

            if (Objects.isNull(fromFieldValue) || Objects.isNull(toFieldValue)) {
                return true;
            }

            LocalDate fromDate = DateUtil.parseLocalDate(fromFieldValue, format);
            LocalDate toDate = DateUtil.parseLocalDate(toFieldValue, format);

            return !Objects.isNull(fromDate) && !fromDate.isAfter(toDate);
        } catch (NoSuchFieldException | IllegalAccessException | DateTimeParseException e) {
            return false;
        }
    }
}
