package com.framework.utils;

import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.util.Properties;

public class ObjectRepository {

    private ObjectRepository() {
    }

    public static By getLocator(
            String module,
            String key) {

        try {

            Properties properties =
                    new Properties();

            properties.load(
                    new FileInputStream(
                            "src/main/resources/objectrepository/"
                                    + module + ".properties"));

            String locator =
                    properties.getProperty(key);

            String[] parts =
                    locator.split("=", 2);

            String type = parts[0];
            String value = parts[1];

            return switch (type.toLowerCase()) {

                case "id" ->
                        By.id(value);

                case "name" ->
                        By.name(value);

                case "xpath" ->
                        By.xpath(value);

                case "css" ->
                        By.cssSelector(value);

                case "classname" ->
                        By.className(value);

                case "tagname" ->
                        By.tagName(value);

                case "linktext" ->
                        By.linkText(value);

                case "partiallinktext" ->
                        By.partialLinkText(value);

                default ->
                        throw new RuntimeException(
                                "Unsupported locator type");
            };

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}