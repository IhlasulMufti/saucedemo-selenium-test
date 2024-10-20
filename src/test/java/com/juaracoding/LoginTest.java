package com.juaracoding;

import com.juaracoding.drivers.DriverSingleton;
import com.juaracoding.pages.LoginPage;
import com.juaracoding.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {

    private WebDriver driver;

    private LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        DriverSingleton.getInstance("firefox");
        driver = DriverSingleton.getDriver();
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage();
    }

    @AfterClass
    public void finish() {
        Utils.delay(5);
        DriverSingleton.closeObjectInstance();
    }

    // positive test
    @Test(priority = 4)
    public void testValidLogin() {
        loginPage.loginUser("standard_user", "secret_sauce");
        Assert.assertEquals(loginPage.getTxtProduct(), "Products");
    }

    // negative test
    // username required
    @Test(priority = 1)
    public void testUsernameRequired() {
        loginPage.loginUser("", "");
        String actual = loginPage.getTxtInvalidUser();
        String expected = "Epic sadface: Username is required";
        System.out.println(actual);
        Assert.assertEquals(actual, expected);
    }

    // password required
    @Test(priority = 2)
    public void testPasswordRequired() {
        loginPage.loginUser("standard_user", "");
        String actual = loginPage.getTxtInvalidUser();
        String expected = "Epic sadface: Password is required";
        System.out.println(actual);
        Assert.assertEquals(actual, expected);
    }

    // username / password is wrong
    @Test(priority = 3)
    public void testInvalidUser() {
        loginPage.loginUser("standard_user", "invalid");
        String actual = loginPage.getTxtInvalidUser();
        String expected = "Epic sadface: Username and password do not match any user in this service";
        System.out.println(actual);
        Assert.assertEquals(actual, expected);
    }

}
