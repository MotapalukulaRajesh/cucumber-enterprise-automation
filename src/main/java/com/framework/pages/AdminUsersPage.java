package com.framework.pages;

public class AdminUsersPage extends BasePage {

    private static final String PAGE = "admin";

    public AdminUsersPage navigateToAdminUsers() {
        click("dashboard", "pimMenu");
        // In real scenario, would click Admin menu and Users
        return this;
    }

    public AdminUsersPage searchUser(String username) {
        type(PAGE, "searchUser", username);
        click(PAGE, "searchUser");
        return this;
    }

    public boolean isUserVisible(String username) {
        return driver.getPageSource().contains(username);
    }
}

