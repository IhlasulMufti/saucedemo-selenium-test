package com.juaracoding;

import com.juaracoding.drivers.DriverSingleton;
import com.juaracoding.pages.LoginPage;
import com.juaracoding.pages.ProductsPage;
import com.juaracoding.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProductTest {

    private WebDriver driver;

    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeClass
    public void setUp() {
        DriverSingleton.getInstance("firefox");
        driver = DriverSingleton.getDriver();
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage();
        productsPage = new ProductsPage();
    }

    @AfterClass
    public void finish() {
        Utils.delay(5);
        DriverSingleton.closeObjectInstance();
    }

    // So we can change from standard_user into error_user
    @AfterMethod
    public void logOut() {
        Utils.delay(2);
        productsPage.logout();
    }

    // positive test
    @Test(priority = 1)
    public void testAddProductToCart() {
        loginPage.loginUser("standard_user", "secret_sauce");
        productsPage.clickProductButton(0);
        Assert.assertEquals(productsPage.getTxtProductButton(0), "Remove");
        Assert.assertEquals(productsPage.getCartTotalItem(), 1);
    }

    @Test(priority = 2)
    public void testRemoveProductFromCart() {
        loginPage.loginUser("standard_user", "secret_sauce");
        productsPage.clickProductButton(0);
        Assert.assertEquals(productsPage.getTxtProductButton(0), "Add to cart");
        Assert.assertEquals(productsPage.getCartTotalItem(), 0);
    }

    // negative test
    @Test(priority = 3)
    public void testFailedAddProduct() {
        loginPage.loginUser("error_user", "secret_sauce");
        productsPage.clickProductButton(2);
        Assert.assertEquals(productsPage.getTxtProductButton(2), "Add to cart");
        Assert.assertEquals(productsPage.getCartTotalItem(), 0);
    }

    @Test(priority = 4)
    public void testFailedRemoveProduct() {
        // pre-condition
        loginPage.loginUser("error_user", "secret_sauce");
        productsPage.clickProductButton(1); // add first
        // step action
        productsPage.clickProductButton(1); // then try remove
        // step validation
        Assert.assertEquals(productsPage.getTxtProductButton(1), "Remove");
        Assert.assertEquals(productsPage.getCartTotalItem(), 1);
    }
}
