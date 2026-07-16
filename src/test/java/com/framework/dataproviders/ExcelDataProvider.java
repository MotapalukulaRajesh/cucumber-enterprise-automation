package com.framework.dataproviders;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "loginData")

    public Object[][] loginData() {

        return new Object[][]{

                {"Admin","admin123"},
                {"Admin","wrongPassword"}
        };
    }
}