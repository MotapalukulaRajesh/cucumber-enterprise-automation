package com.framework.api;

import com.framework.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseAPI {

    public static RequestSpecification getBaseSpec(String sessionCookie) {

        String baseUrl = ConfigReader.get("api.base.url");

        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("origin", baseUrl)
                .addCookie("orangehrm", sessionCookie)
                .build();
    }
}