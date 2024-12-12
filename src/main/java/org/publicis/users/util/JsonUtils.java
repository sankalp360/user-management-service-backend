package org.publicis.users.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static <T> T parseJsonToObject(String jsonData, Class<T> clazz, ObjectMapper objectMapper) throws Exception {
        return objectMapper.readValue(jsonData, clazz);
    }
}
