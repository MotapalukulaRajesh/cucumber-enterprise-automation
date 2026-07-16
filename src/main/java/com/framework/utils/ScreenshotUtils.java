package com.framework.utils;

import com.framework.factory.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {

    private static final Logger LOGGER = Log.getLogger(ScreenshotUtils.class);

    private ScreenshotUtils() {}

    public static String capture(String testName) {

        // Create screenshots directory if it doesn't exist
        File screenshotDir = new File("screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        File source =
                ((TakesScreenshot)
                        DriverFactory.getDriver())
                        .getScreenshotAs(
                                OutputType.FILE);

        String destination =
                "screenshots/" +
                        testName +
                        ".png";

        try {

            FileUtils.copyFile(
                    source,
                    new File(destination));

        } catch (IOException e) {
            LOGGER.error("Failed to capture screenshot for test: {}", testName, e);
        }

        return destination;
    }
}