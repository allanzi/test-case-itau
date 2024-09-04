package com.itau.test.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.test.config.ObjectMapperConfiguration;

public class QueueUtil {
    private static final ObjectMapper objectMapper = ObjectMapperConfiguration.createObjectMapper();

    public static String objectToString(Object object) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T stringToObject(String string, Class<T> clazz) throws JsonProcessingException {
        try {
            return objectMapper.readValue(string, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
