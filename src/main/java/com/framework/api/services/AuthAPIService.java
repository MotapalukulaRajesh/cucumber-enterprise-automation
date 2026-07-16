package com.framework.api.services;

import com.framework.utils.ConfigReader;
import io.restassured.response.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class AuthAPIService {

    private static final String BASE_URL =
            ConfigReader.get("api.base.url");

    private static final String LOGIN_PAGE =
            "/web/index.php/auth/login";

    private static final String AUTH_ENDPOINT =
            "/web/index.php/auth/validate";

    public String getSessionCookie() {

        // Step 1: GET login page and extract hidden csrf token (_token)
        Response loginPage = given()
                .baseUri(BASE_URL)
                .redirects().follow(true)
                .when()
                .get(LOGIN_PAGE);

        String html = loginPage.asString();
        String csrfToken = extractCsrfToken(html);
        String initialSessionCookie = loginPage.getCookie("orangehrm");

        if (csrfToken == null || csrfToken.isEmpty()) {
            throw new RuntimeException(
                    "Could not retrieve csrf _token from login page. Status: "
                    + loginPage.statusCode());
        }

        // Step 2: Submit login form to /auth/validate with csrf token
        String username = ConfigReader.get("api.username");
        String password = ConfigReader.get("api.password");

        Response response = given()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded")
                .cookie("orangehrm", initialSessionCookie)
                .formParam("_token", csrfToken)
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post(AUTH_ENDPOINT);

        String sessionCookie = response.getCookie("orangehrm");
        if ((sessionCookie == null || sessionCookie.isEmpty())
                && initialSessionCookie != null
                && !initialSessionCookie.isEmpty()) {
            sessionCookie = initialSessionCookie;
        }

        if (sessionCookie == null || sessionCookie.isEmpty()) {
            throw new RuntimeException(
                    "Login failed — could not retrieve session cookie."
                    + " Status: " + response.statusCode()
                    + " Body: " + response.body().asString());
        }

        return sessionCookie;
    }

    private String extractCsrfToken(String html) {
        Pattern pattern = Pattern.compile("name=\\\"_token\\\"\\s+value=\\\"([^\\\"]+)\\\"");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
