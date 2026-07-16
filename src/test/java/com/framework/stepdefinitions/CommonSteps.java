package com.framework.stepdefinitions;

import com.framework.context.ContextKey;
import com.framework.context.ScenarioContext;
import com.framework.factory.DriverFactory;
import com.framework.pages.DashboardPage;
import com.framework.pages.LoginPage;
import com.framework.utils.ConfigReader;
import com.framework.utils.Log;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.Logger;

/**
 * CommonSteps - Contains reusable step definitions shared across multiple feature files.
 * This class consolidates common step definitions used across different test scenarios
 * to reduce code duplication and improve maintainability.
 */
public class CommonSteps {

    private static final Logger LOGGER = Log.getLogger(CommonSteps.class);

    private static final String ADMIN_USERNAME = "Admin";
    private static final String ADMIN_PASSWORD = "admin123";

    private final ScenarioContext scenarioContext;

    public CommonSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    /**
     * Launches the application by navigating to the configured URL.
     * This step is executed in the Background section of feature files
     * to ensure the application is available for all scenarios.
     */
    @Given("User launches application")
    public void user_launches_application() {
        LOGGER.info("Launching application");
        DriverFactory.getDriver().get(ConfigReader.get("url"));
        LOGGER.info("Application launched successfully");
    }

    /**
     * Admin login step - supports the "Admin logs in" step definition.
     * Logs in with default admin credentials and stores the DashboardPage
     * in ScenarioContext for use by other step classes.
     */
    @Given("Admin logs in")
    public void adminLogsIn() {
        LOGGER.info("Admin logging in to the system");
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = loginPage.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        scenarioContext.set(ContextKey.DASHBOARD_PAGE, dashboardPage);
        LOGGER.info("Admin successfully logged in");
    }

    /**
     * Admin login step with alternative naming convention "Admin logs into OrangeHRM".
     * This is an alternative step definition for the same action to support
     * different feature file naming conventions.
     */
    @Given("Admin logs into OrangeHRM")
    public void adminLogsIntoOrangeHRM() {
        LOGGER.info("Admin logging into OrangeHRM");
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = loginPage.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        scenarioContext.set(ContextKey.DASHBOARD_PAGE, dashboardPage);
        LOGGER.info("Admin successfully logged into OrangeHRM");
    }
}

