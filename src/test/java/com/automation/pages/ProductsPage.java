/*
 * package com.automation.pages;
 * 
 * import org.openqa.selenium.By; import org.openqa.selenium.WebDriver;
 * 
 * public class ProductsPage {
 * 
 * WebDriver driver;
 * 
 * By addToCartBtn = By.id("add-to-cart-sauce-labs-backpack");
 * 
 * public ProductsPage(WebDriver driver) { this.driver = driver; }
 * 
 * public void addProductToCart() {
 * 
 * driver.findElement(addToCartBtn).click(); } }
 */

package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage {

    WebDriver driver;

    By addToCartBtn =
            By.id("add-to-cart-sauce-labs-backpack");

    By cartIcon =
            By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addProductToCart() {

        driver.findElement(addToCartBtn).click();
    }

    public void openCart() {

        driver.findElement(cartIcon).click();
    }
}