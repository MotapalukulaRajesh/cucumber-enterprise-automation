package com.framework.stepdefinitions;

import com.framework.context.ContextKey;
import com.framework.context.ScenarioContext;
import com.framework.pages.AdminUsersPage;
import com.framework.pages.DashboardPage;
import com.framework.utils.Log;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class AdminUserSteps {

    private static final Logger LOGGER = Log.getLogger(AdminUserSteps.class);

    private final ScenarioContext scenarioContext;
    private AdminUsersPage adminUsersPage;
    private String searchedUsername;

    public AdminUserSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @When("Admin searches user {string}")
    public void adminSearchesUser(String username) {
        LOGGER.info("Searching for user: {}", username);
        searchedUsername = username;
        DashboardPage dashboardPage = (DashboardPage) scenarioContext.get(ContextKey.DASHBOARD_PAGE);
        adminUsersPage = dashboardPage.navigateToAdminUsers();
        adminUsersPage.searchUser(username);
        LOGGER.info("Search initiated for user: {}", username);
    }

    @Then("User should be displayed")
    public void userShouldBeDisplayed() {
        LOGGER.info("Verifying that user '{}' is displayed on the page", searchedUsername);
        Assert.assertNotNull(adminUsersPage, "AdminUsersPage object is null");
        Assert.assertTrue(
                adminUsersPage.isUserVisible(searchedUsername),
                "User '" + searchedUsername + "' is not displayed on the admin users page");
        LOGGER.info("User '{}' verification completed successfully", searchedUsername);
    }
}


