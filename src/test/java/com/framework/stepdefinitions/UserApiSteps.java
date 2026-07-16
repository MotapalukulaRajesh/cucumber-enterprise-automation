package com.framework.stepdefinitions;

import com.framework.api.services.UserAPIService;
import com.framework.context.ContextKey;
import com.framework.context.ScenarioContext;
import com.framework.utils.Log;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class UserApiSteps {

    private static final Logger LOGGER = Log.getLogger(UserApiSteps.class);

    private final ScenarioContext scenarioContext;

    public UserApiSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Given("User with dynamic username is created in Admin if not exists with password {string}")
    public void userCreatedIfNotExists(String password) {

        String dynamicUsername = "AUTO_" + System.currentTimeMillis();
        String firstName = "AutoFirst_" + System.currentTimeMillis();
        String lastName = "AutoLast_" + System.currentTimeMillis();

        Response response = new UserAPIService()
                .createUserIfNotExists(dynamicUsername, password, firstName, lastName);

        LOGGER.info(
                "Admin user API final response for '{}': status={}, body={}",
                dynamicUsername,
                response.statusCode(),
                response.asString());

        Assert.assertTrue(
                response.statusCode() == 200 || response.statusCode() == 201,
                "User operation failed with status: "
                        + response.statusCode()
                        + ", body: "
                        + response.asString());

        // Store dynamic username in context for later verification
        scenarioContext.set(ContextKey.EMPLOYEE_NAME, dynamicUsername);
        scenarioContext.set(ContextKey.USERNAME, dynamicUsername);
        scenarioContext.set(ContextKey.API_RESPONSE, response);

        LOGGER.info("User '{}' is ready for testing", dynamicUsername);
    }

    @Given("User {string} is searched in Admin")
    public void userSearched(String username) {

        Response response = new UserAPIService().searchUser(username);

        Assert.assertEquals(
                response.statusCode(),
                200,
                "User search failed");

        int totalRecords = response.jsonPath().getInt("meta.total");

        if (totalRecords > 0) {
            LOGGER.info("User '{}' found in Admin Users list", username);
        } else {
            LOGGER.info("User '{}' NOT found. Will need to create.", username);
        }

        scenarioContext.set(ContextKey.EMPLOYEE_NAME, username);
    }

    @Given("Admin should be able to verify user in admin users list")
    public void verifyUserInAdminList() {

        String username = scenarioContext.get(ContextKey.EMPLOYEE_NAME).toString();

        Response response = new UserAPIService().searchUser(username);

        Assert.assertEquals(response.statusCode(), 200, "Failed to verify user in admin list");

        int totalRecords = response.jsonPath().getInt("meta.total");

        Assert.assertTrue(totalRecords > 0, "User '" + username + "' not found in Admin Users list");

        LOGGER.info("User '{}' verified in Admin Users list", username);
    }
}
