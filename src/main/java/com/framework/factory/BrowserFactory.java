package com.framework.factory;

import com.framework.constants.FrameworkConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class BrowserFactory {

    private BrowserFactory() {
    }

    public static WebDriver createDriver(String browser) {

        WebDriver driver;

        switch (browser.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default:
                throw new RuntimeException(
                        "Unsupported Browser : " + browser);
        }

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(FrameworkConstants.IMPLICIT_WAIT));

        // Maximize browser window
        driver.manage().window().maximize();

        return driver;
    }
}