package com.framework.api.services;

import com.framework.api.BaseAPI;
import com.framework.factory.DriverFactory;
import com.framework.utils.ConfigReader;
import com.framework.utils.Log;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Cookie;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserAPIService {

    private static final Logger LOGGER = Log.getLogger(UserAPIService.class);

    private static final String USERS_ENDPOINT =
            "/web/index.php/api/v2/admin/users";

    private static final String SEARCH_PARAMS =
            "?limit=50&offset=0&sortField=u.userName&sortOrder=ASC";

    private static final String REFERER =
            ConfigReader.get("api.base.url")
            + "/web/index.php/admin/viewSystemUsers";

    public Response searchUser(String username) {

        String sessionCookie = getSessionCookieFromBrowser();

        return given()
                .spec(BaseAPI.getBaseSpec(sessionCookie))
                .header("referer", REFERER)
                .queryParam("username", username)
                .when()
                .get(USERS_ENDPOINT + SEARCH_PARAMS);
    }

    public Response createUser(String username, String password, String firstName, String lastName) {

        String sessionCookie = getSessionCookieFromBrowser();

        String employeeNumber = createEmployeeForUser(firstName, lastName).trim();
        LOGGER.info(
                "Creating admin user '{}' mapped to employee number '{}'",
                username,
                employeeNumber);

        return tryCreateUser(
                sessionCookie,
                buildUserPayload(username, password, employeeNumber));
    }

    public Response createUserIfNotExists(String username, String password, String firstName, String lastName) {

        Response searchResponse = searchUser(username);

        LOGGER.info(
                "Admin user search response for '{}': status={}, body={}",
                username,
                searchResponse.statusCode(),
                searchResponse.asString());

        if (searchResponse.statusCode() != 200) {
            throw new RuntimeException(
                    "Failed to search admin user before creation. Status: "
                            + searchResponse.statusCode()
                            + ", body: "
                            + searchResponse.asString());
        }

        // Check if user already exists in results
        int totalRecords = searchResponse.jsonPath().getInt("meta.total");

        if (totalRecords > 0) {
            LOGGER.info("User '{}' already exists. Skipping creation.", username);
            return searchResponse;
        }

        LOGGER.info("User '{}' not found. Creating new user...", username);
        return createUser(username, password, firstName, lastName);
    }

    private String getSessionCookieFromBrowser() {
        Cookie cookie = DriverFactory.getDriver().manage().getCookieNamed("orangehrm");
        if (cookie == null || cookie.getValue() == null || cookie.getValue().isEmpty()) {
            throw new RuntimeException(
                    "Could not find 'orangehrm' session cookie in browser. "
                    + "Ensure the scenario logs into OrangeHRM before API user operations.");
        }
        return cookie.getValue();
    }

    private String createEmployeeForUser(String firstName, String lastName) {
        Response employeeResponse = new EmployeeAPIService().createEmployee(firstName, lastName);

        if (!isSuccessful(employeeResponse)) {
            throw new RuntimeException(
                    "Employee creation failed before admin user creation. Status: "
                            + employeeResponse.statusCode()
                            + ", body: "
                            + employeeResponse.asString());
        }

        Object employeeNumber = employeeResponse.jsonPath().get("data.empNumber");

        if (employeeNumber == null) {
            employeeNumber = employeeResponse.jsonPath().get("data.employeeId");
        }

        if (employeeNumber == null) {
            employeeNumber = employeeResponse.jsonPath().get("empNumber");
        }

        if (employeeNumber == null) {
            throw new RuntimeException(
                    "Unable to extract employee number from employee creation response: "
                            + employeeResponse.asString());
        }

        LOGGER.info("Created employee '{}' '{}' with employee number '{}'",
                firstName,
                lastName,
                employeeNumber);

        return String.valueOf(employeeNumber);
    }

    private Map<String, Object> buildUserPayload(
            String username,
            String password,
            String employeeNumber) {

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("empNumber", toEmployeeIdentifierValue(employeeNumber));
        payload.put("userRoleId", 2);
        payload.put("status", true);
        payload.put("username", username);
        payload.put("password", password);
        return payload;
    }

    private Object toEmployeeIdentifierValue(String employeeNumber) {
        try {
            return Integer.parseInt(employeeNumber);
        } catch (NumberFormatException exception) {
            LOGGER.info("Employee number '{}' is not numeric. Sending it as string.", employeeNumber);
            return employeeNumber;
        }
    }

    private Response tryCreateUser(String sessionCookie, Map<String, Object> payload) {
        LOGGER.info("Calling admin user create API with payload keys: {}", payload.keySet());

        Response response = given()
                .spec(BaseAPI.getBaseSpec(sessionCookie))
                .header("referer", REFERER)
                .body(payload)
                .when()
                .post(USERS_ENDPOINT);

        LOGGER.info(
                "Admin user create API response status: {}, body: {}",
                response.statusCode(),
                response.asString());

        return response;
    }

    private boolean isSuccessful(Response response) {
        return response.statusCode() == 200 || response.statusCode() == 201;
    }
}

