package com.framework.pages;

public class DashboardPage extends BasePage {

    public EmployeeSearchPage navigateToPIM() {

        click("dashboard","pimMenu");

        return new EmployeeSearchPage();
    }

    public AdminUsersPage navigateToAdminUsers() {
        click("dashboard", "adminMenu");
        return new AdminUsersPage();
    }
}
