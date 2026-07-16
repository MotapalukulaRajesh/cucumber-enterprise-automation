package com.framework.stepdefinitions;

import com.framework.context.ContextKey;
import com.framework.context.ScenarioContext;
import com.framework.pages.DashboardPage;
import com.framework.pages.EmployeeSearchPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class EmployeeUISteps {

    private final ScenarioContext scenarioContext;

    private EmployeeSearchPage employeeSearchPage;

    public EmployeeUISteps(ScenarioContext scenarioContext) {

        this.scenarioContext = scenarioContext;
    }


    @When("Admin searches employee")
    public void adminSearchesEmployee() {

        String employeeId = scenarioContext.get(ContextKey.EMPLOYEE_ID).toString();

        DashboardPage dashboardPage = (DashboardPage) scenarioContext.get(ContextKey.DASHBOARD_PAGE);
        employeeSearchPage = dashboardPage.navigateToPIM();

        employeeSearchPage.searchEmployeeById(employeeId);
    }

    @Then("Employee should be visible")
    public void employeeShouldBeVisible() {

        String expectedEmployeeId =
                scenarioContext
                        .get(ContextKey.EMPLOYEE_ID)
                        .toString();

        Assert.assertTrue(
                employeeSearchPage.isEmployeeIdVisible(expectedEmployeeId),
                "Employee validation failed for id: " + expectedEmployeeId);
    }
}