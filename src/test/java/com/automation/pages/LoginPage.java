//package com.automation.pages;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//
//public class LoginPage {
//
//    WebDriver driver;
//
//    By username = By.id("user-name");
//    By password = By.id("password");
//    By loginBtn = By.id("login-button");
//
//    public LoginPage(WebDriver driver) {
//        this.driver = driver;
//    }
//
//    public void login(String user, String pass) {
//
//        driver.findElement(username).sendKeys(user);
//
//        driver.findElement(password).sendKeys(pass);
//
//        driver.findElement(loginBtn).click();
//    }
//}

/*
 * package com.automation.pages;
 * 
 * import org.openqa.selenium.By; import org.openqa.selenium.WebDriver;
 * 
 * public class LoginPage {
 * 
 * WebDriver driver;
 * 
 * By username = By.id("user-name"); By password = By.id("password"); By
 * loginBtn = By.id("login-button");
 * 
 * public LoginPage(WebDriver driver) { this.driver = driver; }
 * 
 * public void login(String user, String pass) {
 * 
 * driver.findElement(username).sendKeys(user);
 * 
 * driver.findElement(password).sendKeys(pass);
 * 
 * driver.findElement(loginBtn).click(); } }
 */

package com.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    By username = By.id("user-name");
    By password = By.id("password");
    By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver) {

        this.driver = driver;

        wait = new WebDriverWait(driver,
                Duration.ofSeconds(10));
    }

    public void login(String user, String pass) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(username));

        driver.findElement(username).sendKeys(user);

        driver.findElement(password).sendKeys(pass);

        driver.findElement(loginBtn).click();
    }
}