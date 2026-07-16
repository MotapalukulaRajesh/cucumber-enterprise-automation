package com.framework.constants;

public final class FrameworkConstants {

    private FrameworkConstants() {
    }

    public static final int EXPLICIT_WAIT = 20;

    public static final int IMPLICIT_WAIT = 10;

    public static final String REPORT_PATH =
            System.getProperty("user.dir")
                    + "/reports/ExtentReport.html";

    public static final String SCREENSHOT_PATH =
            System.getProperty("user.dir")
                    + "/screenshots/";

    public static final String QA_CONFIG =
            "src/test/resources/config/qa.properties";

    public static final String UAT_CONFIG =
            "src/test/resources/config/uat.properties";

    public static final String PROD_CONFIG =
            "src/test/resources/config/prod.properties";
}