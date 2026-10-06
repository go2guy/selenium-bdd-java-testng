package com.example.pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
    }

    public void login(String username, String password) {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button"))).click();
    }

    public String dashboardText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("dashboard"))).getText();
    }

    public String errorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("error"))).getText();
    }
}

