package com.framework.utils;

import com.framework.constants.FrameworkConstants;
import com.framework.factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Enhanced Wait Utilities for various wait scenarios in Selenium
 */
public class WaitUtils{

    private WaitUtils() {
    }

    private static WebDriverWait getWait() {
        return new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(FrameworkConstants.EXPLICIT_WAIT));
    }

    /**
     * Wait for element visibility
     */
    public static void waitForVisibility(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element visibility by locator
     */
    public static void waitForVisibility(By locator) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element clickability
     */
    public static void waitForClickability(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element presence in DOM
     */
    public static void waitForPresence(By locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for element invisibility
     */
    public static void waitForInvisibility(By locator) {
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for element invisibility
     */
    public static void waitForInvisibility(WebElement element) {
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Wait for element text to change
     */
    public static void waitForTextPresent(WebElement element, String text) {
        getWait().until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    /**
     * Wait for attribute value
     */
    public static void waitForAttributeValue(By locator, String attribute, String value) {
        getWait().until(ExpectedConditions.attributeToBe(locator, attribute, value));
    }

    /**
     * Wait for URL change
     */
    public static void waitForUrlChange(String currentUrl) {
        getWait().until(ExpectedConditions.urlContains(currentUrl));
    }

    /**
     * Wait for URL to be exactly equal
     */
    public static void waitForUrlToBe(String url) {
        getWait().until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Wait for element to be selected
     */
    public static void waitForElementSelected(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeSelected(element));
    }

    /**
     * Wait for number of elements
     */
    public static void waitForNumberOfElements(By locator, int count) {
        getWait().until(ExpectedConditions.numberOfElementsToBe(locator, count));
    }

    /**
     * Wait for multiple elements visibility
     */
    public static void waitForAllElementsVisible(By locator) {
        getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait for frame and switch to it
     */
    public static void waitForFrameAndSwitch(By locator) {
        getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    /**
     * Wait for frame and switch to it by element
     */
    public static void waitForFrameAndSwitch(WebElement frameElement) {
        getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    /**
     * Wait for alert presence
     */
    public static void waitForAlert() {
        getWait().until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Custom wait with timeout
     */
    public static void waitForCondition(long timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(timeoutSeconds));
    }
}

