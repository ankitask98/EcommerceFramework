package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    WebDriver driver;

    By checkoutButton = By.id("checkout");
    By firstName = By.id("first-name");
    By lastName = By.id("last-name");
    By postalCode = By.id("postal-code");
    By continueButton = By.id("continue");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCheckout() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));

        driver.findElement(checkoutButton).click();

        System.out.println("Checkout button clicked");

        System.out.println("Current URL : " + driver.getCurrentUrl());
    }
    
    public void enterCustomerDetails(String fName, String lName, String zip) throws InterruptedException {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));

        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(postalCode).sendKeys(zip);

        System.out.println("First Name : " +
                driver.findElement(firstName).getAttribute("value"));

        System.out.println("Last Name : " +
                driver.findElement(lastName).getAttribute("value"));

        System.out.println("Zip : " +
                driver.findElement(postalCode).getAttribute("value"));

        driver.findElement(continueButton).click();

        Thread.sleep(3000);

        System.out.println("URL After Continue : "
                + driver.getCurrentUrl());

        System.out.println("Error Count : "
                + driver.findElements(By.cssSelector("[data-test='error']")).size());

        if(driver.findElements(By.cssSelector("[data-test='error']")).size() > 0) {
            System.out.println(driver.findElement(By.cssSelector("[data-test='error']")).getText());
        }
    }
       }
