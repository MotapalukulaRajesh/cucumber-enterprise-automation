package com.framework.api.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class EmployeeAPI {

    public Response createEmployee(
            String firstName,
            String lastName) {

        String payload = """
            {
               "firstName":"%s",
               "lastName":"%s"
            }
            """.formatted(
                firstName,
                lastName);

        return given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/employees");
    }
}