/*
 * package com.automation.base;
 * 
 * import org.openqa.selenium.WebDriver; import
 * org.openqa.selenium.chrome.ChromeDriver; import
 * org.testng.annotations.AfterMethod; import
 * org.testng.annotations.BeforeMethod;
 * 
 * import io.github.bonigarcia.wdm.WebDriverManager;
 * 
 * public class BaseTest {
 * 
 * public static WebDriver driver;
 * 
 * @BeforeMethod public void setup() {
 * 
 * WebDriverManager.chromedriver().setup();
 * 
 * driver = new ChromeDriver();
 * 
 * driver.manage().window().maximize();
 * 
 * driver.get("https://www.saucedemo.com/"); }
 * 
 * @AfterMethod public void tearDown() throws InterruptedException {
 * //Thread.sleep(5000); // 5 seconds
 * 
 * //driver.quit(); } }
 */

package com.automation.base;

import java.io.IOException;
import com.automation.config.ConfigReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.automation.utils.ScreenshotUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    public static WebDriver driver;

    ConfigReader config = new ConfigReader();
    @BeforeMethod
    public void setup() {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(config.getUrl());
    }

    @AfterMethod
    public void tearDown(ITestResult result)
            throws IOException {

        if (result.getStatus()
                == ITestResult.FAILURE) {

            System.out.println("Taking Screenshot");

            ScreenshotUtil.captureScreenshot(
                    driver,
                    result.getName());
        }

        driver.quit();
    }
}