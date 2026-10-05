package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    private Logger log = LogManager.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public WebDriver getDriver() {
        return driver;
    }

    // =========================================================
    // Find Single Element
    // =========================================================

    public WebElement findElement(By locator) {

        log.debug("Finding element with locator: {}", locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        WebElement element = driver.findElement(locator);
        log.debug("Element found successfully: {}", locator);

        return element;
    }

    // =========================================================
    // Find Multiple Elements
    // =========================================================

    public List<WebElement> findElements(By locator) {

        log.debug("Finding multiple elements with locator: {}", locator);
        List<WebElement> elements = driver.findElements(locator);
        log.debug("Found {} element(s) with locator: {}", elements.size(), locator);
        return elements;
    }

    // =========================================================
    // Find Single Element With Custom Timeout
    // =========================================================

    public WebElement findElement(By locator, Duration duration) {
        log.debug("Finding element with locator: {} using timeout: {} seconds", locator, duration.getSeconds());
        WebDriverWait customWait = new WebDriverWait(driver, duration);
        log.debug("Element found successfully: {}", locator);
        customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }
}