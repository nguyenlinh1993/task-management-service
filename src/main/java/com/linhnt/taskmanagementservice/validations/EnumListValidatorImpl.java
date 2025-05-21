package com.linhnt.taskmanagementservice.validations;

import com.linhnt.taskmanagementservice.utils.ReflectionUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class EnumListValidatorImpl implements ConstraintValidator<EnumListValidator, List<String>> {
    private List<Object> valueList;

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null) {
            return true; // allow null, use @NotEmpty or @NotNull on the field to disallow null values
        }
        for (String value : values) {
            if (!valueList.contains(value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void initialize(EnumListValidator constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();
        for (Enum enumVal : enumClass.getEnumConstants()) {
            valueList.add(ReflectionUtil.getField(enumVal, constraintAnnotation.fieldName()));
        }
    }
}
