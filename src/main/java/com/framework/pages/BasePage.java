package com.framework.pages;

import com.framework.factory.DriverFactory;
import com.framework.utils.ObjectRepository;
import com.framework.utils.WaitUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * Enhanced Base Page with additional utility methods for common web interactions
 */
public class BasePage {

    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }

    /**
     * Click on element
     */
    protected void click(String propertyFile, String locatorName) {
        WaitUtils.waitForClickability(
                driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName)));
        driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName)).click();
    }

    /**
     * Send keys to element
     */
    protected void type(String propertyFile, String locatorName, String value) {
        WaitUtils.waitForVisibility(
                driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName)));
        WebElement element = driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName));
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Get text from element
     */
    protected String getText(String propertyFile, String locatorName) {
        WaitUtils.waitForVisibility(
                driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName)));
        return driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName)).getText();
    }

    /**
     * Check if element is displayed
     */
    protected boolean isDisplayed(String propertyFile, String locatorName) {
        try {
            return driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is enabled
     */
    protected boolean isEnabled(String propertyFile, String locatorName) {
        try {
            return driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName)).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get attribute value
     */
    protected String getAttribute(String propertyFile, String locatorName, String attributeName) {
        return driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName))
                .getAttribute(attributeName);
    }

    /**
     * Hover on element
     */
    protected void hover(String propertyFile, String locatorName) {
        WebElement element = driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName));
        new Actions(driver).moveToElement(element).perform();
    }

    /**
     * Scroll to element
     */
    protected void scrollToElement(String propertyFile, String locatorName) {
        WebElement element = driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scroll page down
     */
    protected void scrollPageDown(int pixelAmount) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + pixelAmount + ")");
    }

    /**
     * Scroll page up
     */
    protected void scrollPageUp(int pixelAmount) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-" + pixelAmount + ")");
    }

    /**
     * Select dropdown by value
     */
    protected void selectByValue(String propertyFile, String locatorName, String value) {
        WebElement element = driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName));
        new Select(element).selectByValue(value);
    }

    /**
     * Select dropdown by visible text
     */
    protected void selectByVisibleText(String propertyFile, String locatorName, String text) {
        WebElement element = driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName));
        new Select(element).selectByVisibleText(text);
    }

    /**
     * Select dropdown by index
     */
    protected void selectByIndex(String propertyFile, String locatorName, int index) {
        WebElement element = driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName));
        new Select(element).selectByIndex(index);
    }

    /**
     * Get selected option from dropdown
     */
    protected String getSelectedOption(String propertyFile, String locatorName) {
        WebElement element = driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName));
        return new Select(element).getFirstSelectedOption().getText();
    }

    /**
     * Upload file to file input element
     */
    protected void uploadFile(String propertyFile, String locatorName, String filePath) {
        WebElement element = driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName));
        element.sendKeys(filePath);
    }

    /**
     * Switch to frame by name or ID
     */
    protected void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }

    /**
     * Switch to frame by index
     */
    protected void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }

    /**
     * Switch to frame by element
     */
    protected void switchToFrame(WebElement frameElement) {
        driver.switchTo().frame(frameElement);
    }

    /**
     * Switch back to default content
     */
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    /**
     * Handle alert - Accept
     */
    protected void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    /**
     * Handle alert - Dismiss
     */
    protected void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    /**
     * Get alert text
     */
    protected String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    /**
     * Type text in alert
     */
    protected void typeInAlert(String text) {
        driver.switchTo().alert().sendKeys(text);
    }

    /**
     * Execute JavaScript
     */
    protected Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    /**
     * Execute JavaScript asynchronously
     */
    protected Object executeAsyncScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeAsyncScript(script, args);
    }

    /**
     * Get CSS property value
     */
    protected String getCSSValue(String propertyFile, String locatorName, String propertyName) {
        return driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName))
                .getCssValue(propertyName);
    }

    /**
     * Double click on element
     */
    protected void doubleClick(String propertyFile, String locatorName) {
        WebElement element = driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName));
        new Actions(driver).doubleClick(element).perform();
    }

    /**
     * Right click on element
     */
    protected void rightClick(String propertyFile, String locatorName) {
        WebElement element = driver.findElement(ObjectRepository.getLocator(propertyFile, locatorName));
        new Actions(driver).contextClick(element).perform();
    }

    /**
     * Drag and drop element
     */
    protected void dragAndDrop(String sourcePropertyFile, String sourceLocatorName,
                               String targetPropertyFile, String targetLocatorName) {
        WebElement sourceElement = driver.findElement(
                ObjectRepository.getLocator(sourcePropertyFile, sourceLocatorName));
        WebElement targetElement = driver.findElement(
                ObjectRepository.getLocator(targetPropertyFile, targetLocatorName));
        new Actions(driver).dragAndDrop(sourceElement, targetElement).perform();
    }

    /**
     * Wait for element visibility
     */
    protected void waitForElementVisible(String propertyFile, String locatorName) {
        WaitUtils.waitForVisibility(
                ObjectRepository.getLocator(propertyFile, locatorName));
    }

    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Refresh page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Navigate back
     */
    protected void navigateBack() {
        driver.navigate().back();
    }

    /**
     * Navigate forward
     */
    protected void navigateForward() {
        driver.navigate().forward();
    }
}

