package com.framework.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentManager {

    private static final Properties properties =
            new Properties();

    static {

        String env =
                System.getProperty("env", "qa");

        String filePath =
                "src/test/resources/config/"
                        + env
                        + ".properties";

        try {

            FileInputStream fis =
                    new FileInputStream(filePath);

            properties.load(fis);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load environment file : "
                            + filePath);
        }
    }

    public static String getProperty(String key) {

        return properties.getProperty(key);
    }
}