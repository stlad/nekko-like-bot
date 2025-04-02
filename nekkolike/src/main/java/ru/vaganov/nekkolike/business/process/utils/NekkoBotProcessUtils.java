package ru.vaganov.nekkolike.business.process.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NekkoBotProcessUtils {

    public static <T> T mapArguments(String args, ObjectMapper objectMapper, Class<T> targetClass) {
        if (args == null) {
            return null;
        }
        try {
            return objectMapper.readValue(args, targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
