package com.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FinishPage {

    WebDriver driver;

    By finishButton = By.id("finish");
    By successMessage = By.className("complete-header");

    public FinishPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickFinish() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(finishButton));

        driver.findElement(finishButton).click();
    }

    public String getSuccessMessage() {

    	WebDriverWait wait =

    	new WebDriverWait(driver, Duration.ofSeconds(10));

    	wait.until(ExpectedConditions.urlContains("checkout-complete.html"));

    	wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));

    	return driver.findElement(successMessage).getText();

    	}
}