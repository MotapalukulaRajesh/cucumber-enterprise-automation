package com.framework.hooks;

import com.aventstack.extentreports.ExtentTest;
import com.framework.config.EnvironmentManager;
import com.framework.factory.BrowserFactory;
import com.framework.factory.DriverFactory;
import com.framework.reports.ExtentReportManager;
import com.framework.reports.ExtentTestManager;
import com.framework.utils.ConfigReader;
import com.framework.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        String browser = EnvironmentManager.getProperty("browser");
        WebDriver driver = BrowserFactory.createDriver(browser);
        DriverFactory.setDriver(driver);
        ExtentTest extentTest = ExtentReportManager.getReport().createTest(scenario.getName());
        ExtentTestManager.setTest(extentTest);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentTestManager.getTest().fail("Scenario Failed");
            ScreenshotUtils.capture(scenario.getName());
        }
        else {
            ExtentTestManager.getTest().pass("Scenario Passed");
        }
        DriverFactory.quitDriver();
        ExtentReportManager.getReport().flush();
    }

}