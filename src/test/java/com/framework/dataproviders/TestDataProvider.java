package com.framework.dataproviders;

import com.framework.utils.ExcelUtil;
import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "loginData")
    public static Object[][] loginData() {

        return ExcelUtil.getData(
                "src/test/resources/testdata/LoginData.xlsx",
                "Login");
    }
}