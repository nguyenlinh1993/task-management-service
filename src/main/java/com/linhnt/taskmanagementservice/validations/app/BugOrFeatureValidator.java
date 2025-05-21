package com.linhnt.taskmanagementservice.validations.app;

import com.linhnt.taskmanagementservice.dto.request.CreateTaskRequest;
import com.linhnt.taskmanagementservice.dto.request.UpdateTaskRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BugOrFeatureValidator implements ConstraintValidator<BugOrFeature, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        switch (value) {
            case null -> {
                return true;
            }
            case CreateTaskRequest e -> {
                boolean hasBug = e.getBug() != null;
                boolean hasFeature = e.getFeature() != null;

                return hasBug ^ hasFeature;
            }
            case UpdateTaskRequest e -> {
                boolean hasBug = e.getBug() != null;
                boolean hasFeature = e.getFeature() != null;

                return hasBug ^ hasFeature;
            }
            default -> {
            }
        }
        return false;
    }
}