package com.framework.factory;

import org.openqa.selenium.WebDriver;

public final class DriverFactory {

    private DriverFactory() {
    }

    private static final ThreadLocal<WebDriver> DRIVER =
            new ThreadLocal<>();

    public static void setDriver(WebDriver driver) {

        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {

        return DRIVER.get();
    }

    public static void quitDriver() {

        if (DRIVER.get() != null) {

            DRIVER.get().quit();
            DRIVER.remove();
        }
    }
}