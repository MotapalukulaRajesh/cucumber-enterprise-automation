package com.framework.pages;

public class LoginPage extends BasePage {

    public DashboardPage login(String username, String password) {

        type("login", "username", username);

        type("login", "password", password);

        click("login", "loginButton");

        waitForPostLoginNavigation();

        return new DashboardPage();
    }

    private void waitForPostLoginNavigation() {
        long endTime = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < endTime) {
            String currentUrl = getCurrentUrl();
            if (currentUrl != null && !currentUrl.contains("/auth/login")) {
                return;
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting for login navigation", e);
            }
        }
        throw new RuntimeException("Login did not navigate away from auth page within timeout");
    }

    public void enterUsername(String username) {
        type("login", "username", username);
    }

    public void enterPassword(String password) {
        type("login", "password", password);
    }

    public void clickLogin() {
        click("login", "loginButton");
    }

    public String getDashboardText() {
        String url = getCurrentUrl();
        if (url != null && url.contains("/dashboard")) {
            return "Dashboard";
        }
        return "";
    }
}