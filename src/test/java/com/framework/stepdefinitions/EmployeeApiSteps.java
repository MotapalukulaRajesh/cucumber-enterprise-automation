package com.framework.stepdefinitions;

import com.framework.api.services.EmployeeAPIService;
import com.framework.context.ContextKey;
import com.framework.context.ScenarioContext;
import com.framework.utils.Log;

import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;

public class EmployeeApiSteps {

    private static final Logger LOGGER = Log.getLogger(EmployeeApiSteps.class);

    private final ScenarioContext scenarioContext;

    public EmployeeApiSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Given("Employee {string} is created using API")
    public void employeeCreatedUsingAPI(String firstName) {

        Response response = new EmployeeAPIService().createEmployee(firstName,"Automation");

        Assert.assertEquals( response.statusCode(),200,"Employee creation failed");

        String employeeId = response.jsonPath().getString("data.empNumber");

        if (employeeId == null || employeeId.isEmpty()) {
            employeeId = response.jsonPath().getString("id");
        }

        Assert.assertNotNull(employeeId, "Employee id missing in API response: " + response.asString());

        scenarioContext.set(ContextKey.EMPLOYEE_ID,employeeId);

        scenarioContext.set(ContextKey.EMPLOYEE_NAME,firstName);

        scenarioContext.set(ContextKey.API_RESPONSE,response);

        LOGGER.info("Employee created successfully");
        LOGGER.info("Employee Id = {}", employeeId);
        LOGGER.info("Employee Name = {}", firstName);
    }
}