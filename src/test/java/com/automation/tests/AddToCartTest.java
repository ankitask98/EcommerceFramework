package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.pages.CartPage;
import com.automation.pages.CheckoutPage;
import com.automation.pages.FinishPage;
import com.automation.pages.LoginPage;
import com.automation.pages.ProductsPage;
import org.openqa.selenium.By;



public class AddToCartTest extends BaseTest {

    @Test
    public void checkoutTest()throws InterruptedException{

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        ProductsPage product = new ProductsPage(driver);
        product.addProductToCart();
        product.openCart();

        CartPage cart = new CartPage(driver);

        Assert.assertEquals(
                cart.getProductName(),
                "Sauce Labs Backpack");

        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);

        checkout.enterCustomerDetails(
                "Ankita",
                "Karoshi",
                "560001");
  
        
        FinishPage finish = new FinishPage(driver);
        System.out.println(driver.getCurrentUrl());
        
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());

        System.out.println("Finish Button Count: " +
                driver.findElements(By.id("finish")).size());

        System.out.println(driver.getPageSource());

        finish.clickFinish();

        Assert.assertEquals(
                finish.getSuccessMessage(),
                "Thank you for your order!");

        System.out.println("Order placed successfully");
    }
}