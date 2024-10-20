package com.juaracoding.pages;

import com.juaracoding.drivers.DriverSingleton;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsPage {

    private WebDriver driver;

    public ProductsPage(){
        this.driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(@class, 'btn_inventory')]")
    private List<WebElement> productButtons;

    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    private WebElement cartTotalItem;

    @FindBy(xpath = "//button[@id='react-burger-menu-btn']")
    private WebElement menuExpander;

    @FindBy(xpath = "//a[@id='logout_sidebar_link']")
    private WebElement btnLogout;

    // button for add / remove product from cart
    public void clickProductButton(int itemIndex) {
        productButtons.get(itemIndex).click();
    }

    public String getTxtProductButton(int itemIndex) {
        return productButtons.get(itemIndex).getText();
    }

    public int getCartTotalItem(){
        int total;
        try {
            total = Integer.parseInt(cartTotalItem.getText());
        } catch (NoSuchElementException e) {
            total = 0;
        }
        return total;
    }

    public void logout() {
        menuExpander.click();
        btnLogout.click();
    }

}
