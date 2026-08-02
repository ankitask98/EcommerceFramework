package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    WebDriver driver;

    By productName =
            By.className("inventory_item_name");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getProductName() {

        return driver.findElement(productName)
                     .getText();
    }
    By checkoutButton = By.id("checkout");

    public void clickCheckout() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton))
                .click();

        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));

        System.out.println("Navigated to Checkout Step One");
    }
}