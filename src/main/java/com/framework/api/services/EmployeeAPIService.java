package com.framework.api.services;

import com.framework.api.BaseAPI;
import com.framework.factory.DriverFactory;
import com.framework.utils.ConfigReader;
import io.restassured.response.Response;
import org.openqa.selenium.Cookie;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class EmployeeAPIService {

    private static final String EMPLOYEE_ENDPOINT =
            "/web/index.php/api/v2/pim/employees";

    private static final String REFERER =
            ConfigReader.get("api.base.url")
            + "/web/index.php/pim/addEmployee";

    public Response createEmployee(String firstName, String lastName) {
        String sessionCookie = getSessionCookieFromBrowser();

        Map<String, Object> payload = new HashMap<>();
        payload.put("firstName", firstName);
        payload.put("middleName", "");
        payload.put("lastName", lastName);
        payload.put("empPicture", null);
        payload.put("employeeId", "");

        return given()
                .spec(BaseAPI.getBaseSpec(sessionCookie))
                .header("referer", REFERER)
                .body(payload)
                .when()
                .post(EMPLOYEE_ENDPOINT);
    }

    private String getSessionCookieFromBrowser() {
        Cookie cookie = DriverFactory.getDriver().manage().getCookieNamed("orangehrm");
        if (cookie == null || cookie.getValue() == null || cookie.getValue().isEmpty()) {
            throw new RuntimeException(
                    "Could not find 'orangehrm' session cookie in browser. "
                    + "Ensure the scenario logs into OrangeHRM before API employee creation.");
        }
        return cookie.getValue();
    }
}