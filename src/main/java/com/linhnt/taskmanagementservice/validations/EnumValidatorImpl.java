package com.linhnt.taskmanagementservice.validations;

import com.linhnt.taskmanagementservice.utils.ReflectionUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, Object> {
    private List<Object> valueList;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value == null || valueList.contains(value);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void initialize(EnumValidator constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();
        for (Enum enumVal : enumClass.getEnumConstants()) {
            valueList.add(ReflectionUtil.getField(enumVal, constraintAnnotation.fieldName()));
        }
    }
}
