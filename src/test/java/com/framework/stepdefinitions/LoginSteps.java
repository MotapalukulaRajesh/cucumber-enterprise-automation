package com.framework.stepdefinitions;

import com.framework.dataproviders.TestDataProvider;
import com.framework.factory.DriverFactory;
import com.framework.pages.LoginPage;
import com.framework.utils.Log;
import io.cucumber.java.en.*;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class LoginSteps {

    private static final Logger LOGGER = Log.getLogger(LoginSteps.class);

    private final LoginPage loginPage = new LoginPage();


    @When("User logs in using excel row {int}")
    public void userLogsInUsingExcelRow(int rowNumber) {
        Object[][] loginData = TestDataProvider.loginData();

        if (rowNumber < 1 || rowNumber > loginData.length) {
            throw new IllegalArgumentException("Excel row out of range: " + rowNumber);
        }

        String username = loginData[rowNumber - 1][0].toString();
        String password = loginData[rowNumber - 1][1].toString();

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Then("User should navigate to dashboard")
    public void verifyDashboard() {
        Assert.assertEquals(loginPage.getDashboardText(),"Dashboard");
    }

    @When("User enters username {string}")
    public void userEntersUsername(String username) {
        LOGGER.info("Entering username: {}", username);
        loginPage.enterUsername(username);
    }

    @And("User enters password {string}")
    public void userEntersPassword(String password) {
        LOGGER.info("Entering password");
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Then("User should see {string}")
    public void userShouldSeeResult(String result) {
        LOGGER.info("Verifying login result: {}", result);

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        String pageSource = DriverFactory.getDriver().getPageSource();
        boolean isDashboardVisible = currentUrl.contains("/dashboard/index") || pageSource.contains("Dashboard");
        boolean isErrorVisible = pageSource.contains("Invalid") || pageSource.contains("error") || pageSource.contains("credentials");

        if (result.equalsIgnoreCase("Success")) {
            Assert.assertTrue(isDashboardVisible, "Dashboard should be visible for successful login");
            LOGGER.info("Login successful - Dashboard is visible");
        } else if (result.equalsIgnoreCase("Failure")) {
            Assert.assertTrue(isErrorVisible || !isDashboardVisible, "Login should fail with error message");
            LOGGER.info("Login failed as expected - Error message displayed");
        } else {
            throw new IllegalArgumentException("Invalid result type: " + result + ". Expected 'Success' or 'Failure'");
        }
    }
}