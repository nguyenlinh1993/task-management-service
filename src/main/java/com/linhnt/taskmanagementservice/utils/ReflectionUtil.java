package com.linhnt.taskmanagementservice.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.util.Arrays;

public final class ReflectionUtil {
    private ReflectionUtil() {
    }

    /**
     * Invokes a method by its name on a given object using Java Reflection.
     *
     * @param methodName The name of the method to be invoked
     * @param object     The object on which the method should be called
     * @param params     The parameters to be passed to the method
     * @param <T>        The generic return type of the method
     * @return The result of invoking the method
     * @throws RuntimeException If there's an issue invoking the method by reflection
     */
    public static <T> T callMethod(String methodName, Object object, Object... params) {
        try {
            //noinspection unchecked
            return (T) object.getClass().getMethod(methodName).invoke(object, params);
        } catch (Exception e) {
            String message = "Fail to call method by reflection: method name = " + methodName + " , object: " + object.toString() + "\n" + "params: " + Arrays.toString(params);
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Retrieves the value of a field from a given object using Java Reflection.
     *
     * @param object    The object from which to retrieve the field value
     * @param fieldName The name of the field to retrieve
     * @param <T>       The generic type of the field
     * @return The value of the field
     * @throws RuntimeException If there's an issue accessing the field by reflection
     */
    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    public static <T> T getField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            if (!field.canAccess(object)) {
                field.setAccessible(true);
            }
            //noinspection unchecked
            return (T) field.get(object);
        } catch (Exception e) {
            String message = "Fail to call method by reflection: field name = " + fieldName + ", object: " + object.toString();
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Set the value of a field from a given object using Java Reflection.
     *
     * @param object    The object from which to retrieve the field value
     * @param fieldName The name of the field to retrieve
     * @param value     The value to set
     */
    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    public static void setField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            if (!field.canAccess(object)) {
                field.setAccessible(true);
            }
            field.set(object, value);
        } catch (Exception e) {
            String message = "Fail to call method by reflection: field name = " + fieldName + ", object: " + object.toString();
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Retrieves the value of a field annotated with @JsonProperty from a given object using Java Reflection.
     *
     * @param object       The object from which to retrieve the field value
     * @param propertyName The name specified in the @JsonProperty annotation of the field
     * @param <T>          The generic type of the field
     * @return The value of the field annotated with @JsonProperty
     * @throws RuntimeException If the field with the specified property name is not found or there's an issue accessing the field by reflection
     */
    @SuppressWarnings({"PMD.AvoidAccessibilityAlteration", "PMD.AvoidDeeplyNestedIfStmts"})
    public static <T> T getFieldValueByJsonProperty(Object object, String propertyName) {
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(JsonProperty.class)) {
                    JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                    if (jsonProperty.value().equals(propertyName)) {
                        if (!field.canAccess(object)) {
                            field.setAccessible(true);
                        }
                        //noinspection unchecked
                        return (T) field.get(object);
                    }
                }
            }
        } catch (Exception e) {
            String message = "Failed to retrieve field by reflection: propertyName = " + propertyName + ", object: " + object.toString();
            throw new RuntimeException(message, e);
        }
        throw new RuntimeException("No field with propertyName = " + propertyName + " found.");
    }
}
