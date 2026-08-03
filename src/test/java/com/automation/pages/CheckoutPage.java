package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    WebDriver driver;

	/*
	 * By checkoutButton = By.id("checkout");
	 */   
    By firstName = By.id("first-name");
    By lastName = By.id("last-name");
    By postalCode = By.id("postal-code");
    By continueButton = By.id("continue");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

	/*
	 * public void clickCheckout() {
	 * 
	 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	 * 
	 * wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
	 * 
	 * driver.findElement(checkoutButton).click();
	 * 
	 * System.out.println("Checkout button clicked");
	 * 
	 * System.out.println("Current URL : " + driver.getCurrentUrl()); }
	 */
    
    public void enterCustomerDetails(String fName, String lName, String zip) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement first = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        WebElement last = wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
//        WebElement postal = wait.until(ExpectedConditions.visibilityOfElementLocated(postalCode));
        WebElement postal = wait.until(ExpectedConditions.elementToBeClickable(postalCode));

        first.clear();
        first.sendKeys(fName);

        last.click();
        last.clear();
        last.sendKeys(lName);

        wait.until(ExpectedConditions.elementToBeClickable(postal));

        postal.click();
        postal.clear();
        postal.sendKeys(zip);

        System.out.println("Zip Field = " + postal.getDomProperty("value"));

        System.out.println("Zip Parameter = " + zip);
        System.out.println("Zip Field = " + postal.getAttribute("value"));

        WebElement continueBtn = wait.until(
                ExpectedConditions.elementToBeClickable(continueButton));

        continueBtn.click();

        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));

        System.out.println("Moved to Step Two");
    }
}
