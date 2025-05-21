package com.linhnt.taskmanagementservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Utility class for JSON serialization and deserialization.
 */
public final class JsonUtil {
    /**
     * ObjectMapper instance.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

    /**
     * Parse JSON string to object.
     *
     * @param json   JSON string
     * @param tClass Class of object
     * @param <T>    Type of object
     * @return Object
     */
    public static <T> T parseJson(String json, Class<T> tClass) {
        try {
            return OBJECT_MAPPER.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing " + tClass.getSimpleName(), e);
        }
    }

    public static <T> T parseJson(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing " + typeReference.getType().getTypeName());
        }
    }

    /**
     * Parse JSON string to list of objects.
     *
     * @param json   JSON string
     * @param tClass Class of object
     * @param <T>    Type of object
     * @return List of objects
     */
    public static <T> List<T> parseJsonList(String json, Class<T> tClass) {
        try {
            return OBJECT_MAPPER.readValue(json, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, tClass));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing list of " + tClass.getSimpleName(), e);
        }
    }

    /**
     * Serialize object to JSON string.
     *
     * @param list Object
     * @param <T>  Type of object
     * @return JSON string
     */
    public static <T> String toJson(T list) {
        try {
            return OBJECT_MAPPER.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing " + list.getClass().getSimpleName(), e);
        }
    }
}
