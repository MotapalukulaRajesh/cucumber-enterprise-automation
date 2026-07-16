package com.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class JsonUtil {

    public static String getValue(
            String filePath,
            String key) {

        try {

            ObjectMapper mapper =
                    new ObjectMapper();

            JsonNode node =
                    mapper.readTree(
                            new File(filePath));

            return node.get(key).asText();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}