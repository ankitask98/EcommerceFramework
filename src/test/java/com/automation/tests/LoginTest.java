package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.data.TestData;
import com.automation.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData",
            dataProviderClass = TestData.class)

    public void verifyLogin(String username, String password) {

        LoginPage login = new LoginPage(driver);

        login.login(username, password);

        Assert.assertEquals(
                driver.getTitle(),
                "Swag Labs");

        System.out.println("Login Successful for : " + username);
    }
}
