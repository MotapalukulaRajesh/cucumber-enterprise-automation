package com.framework.pages;

public class EmployeeSearchPage
        extends BasePage {

    private static final String PAGE =
            "employee";

    public EmployeeSearchPage
    searchEmployee(String employeeName) {

        type(
                PAGE,
                "employeeSearch",
                employeeName);

        click(
                PAGE,
                "searchButton");

        return this;
    }

    public EmployeeSearchPage searchEmployeeById(String employeeId) {
        type(
                PAGE,
                "employeeIdSearch",
                employeeId);

        click(
                PAGE,
                "searchButton");

        return this;
    }

    public String getEmployeeName() {

        return getText(
                PAGE,
                "employeeName");
    }

    public boolean isEmployeeVisible(String employeeName) {
        return driver.getPageSource().contains(employeeName);
    }

    public boolean isEmployeeIdVisible(String employeeId) {
        return driver.getPageSource().contains(employeeId);
    }
}