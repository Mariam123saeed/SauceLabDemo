package org.example.pages.cart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckOutPage extends BasePage {

    private Logger log = LogManager.getLogger(CheckOutPage.class);

    // =========================================
    // Checkout: Your Information
    // =========================================

    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");

    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");

    private final By errorMessage = By.cssSelector("[data-test='error']");


    // =========================================
    // Checkout: Overview
    // =========================================

    private final By subtotal = By.cssSelector("[data-test='subtotal-label']");
    private final By tax = By.cssSelector("[data-test='tax-label']");
    private final By total = By.cssSelector("[data-test='total-label']");
    private final By finishButton = By.id("finish");


    // =========================================
    // Checkout Complete
    // =========================================

    private final By completeHeader = By.cssSelector("[data-test='complete-header']");
    private final By completeText = By.cssSelector("[data-test='complete-text']");
    private final By backHomeButton = By.id("back-to-products");
    private final By generatePdfButton = By.id("generate-pdf-order");


    // =========================================
    // Constructor
    // =========================================

    public CheckOutPage(WebDriver driver) {
        super(driver);
    }


    // =========================================
    // Your Information
    // =========================================

    public WebElement getFirstName() {
        log.info("Getting first name field");
        return findElement(firstName);
    }

    public WebElement getLastName() {
        log.info("Getting last name field");
        return findElement(lastName);
    }

    public WebElement getPostalCode() {
        log.info("Getting postal code field");
        return findElement(postalCode);
    }

    public WebElement getContinueButton() {
        log.info("Getting continue button");
        return findElement(continueButton);
    }

    public WebElement getCancelButton() {
        log.info("Getting cancel button");
        return findElement(cancelButton);
    }

    public String getErrorMessage() {
        log.info("Getting error message locator");
        return findElement(errorMessage).getText();
    }


    // =========================================
    // Actions
    // =========================================

    public void enterFirstName(String value) {

        getFirstName().sendKeys(value);
        log.debug("Enter FirstName: {}", value);
    }

    public void enterLastName(String value) {

        getLastName().sendKeys(value);
        log.debug("Enter LastName: {}", value);
    }

    public void enterPostalCode(String value) {

        getPostalCode().sendKeys(value);
        log.debug("Enter postalCode: {}", value);
    }

    public void clickContinue() {
        getContinueButton().click();
        log.info("Continue button clicked");
    }

    public void clickCancel() {

        getCancelButton().click();
        log.info("Cancel button clicked");
    }

    public void fillCustomerInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void continueToOverview(String firstName, String lastName, String postalCode) {
        fillCustomerInformation(firstName, lastName, postalCode);
        clickContinue();
    }


    // =========================================
    // Overview
    // =========================================

    public WebElement getSubtotal() {
        log.info("Getting sub total locator");
        return findElement(subtotal);
    }

    public WebElement getTax() {
        log.info("Getting tax locator");
        return findElement(tax);
    }

    public WebElement getTotal() {
        log.info("Getting total locator");
        return findElement(total);
    }

    public WebElement getFinishButton() {
        log.info("Getting finish button");
        return findElement(finishButton);
    }

    public double getSubtotalValue() {

        String text = getSubtotal().getText();
        log.info("Subtotal value retrieved: {} ", subtotal);
        return Double.parseDouble(text.replace("Item total: $", "").trim());
    }

    public double getTaxValue() {

        String text = getTax().getText();
        log.info("Tax retrieved: {}" , tax);
        return Double.parseDouble(text.replace("Tax: $", "").trim());
    }

    public double getTotalValue() {

        String text = getTotal().getText();
        log.info("Total value retrieved: {}", total);
        return Double.parseDouble(text.replace("Total: $", "").trim());
    }

    public void clickFinish() {

        getFinishButton().click();
        log.info("Cancel finish clicked");
    }


    // =========================================
    // Checkout Complete
    // =========================================

    public WebElement getCompleteHeader() {
        log.info("Getting header of complete page");
        return findElement(completeHeader);
    }

    public WebElement getCompleteText() {
        log.info("Getting complete text locate");
        return findElement(completeText);
    }

    public WebElement getBackHomeButton() {
        log.info("Getting back home button locator");
        return findElement(backHomeButton);
    }

    public WebElement getGeneratePdfButton() {
        log.info("Getting generate pdf button locator");
        return findElement(generatePdfButton);
    }

    public String getCompleteHeaderText() {
        log.info("Checkout complete header: {} ", completeHeader);
        return getCompleteHeader().getText();
    }

    public String getCompleteTextValue() {
        log.info("Checkout tax value: {}",tax);
        return getCompleteText().getText();

    }

    public void clickBackHome() {

        getBackHomeButton().click();
        log.info("Back home button clicked");

    }

    public void clickGeneratePdf() {

        getGeneratePdfButton().click();
        log.info("Generate pdf button clicked");
    }
}