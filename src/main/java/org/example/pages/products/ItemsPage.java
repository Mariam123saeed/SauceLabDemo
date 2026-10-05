package org.example.pages.products;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ItemsPage extends BasePage {

    private Logger log = LogManager.getLogger(ItemsPage.class);

    private final By products = By.className("inventory_item");
    private final By productNames = By.className("inventory_item_name");
    private final By addToCartButtons = By.cssSelector("button[id^='add-to-cart-']");
    private final By removeButtons = By.cssSelector("button[id^='remove-']");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By backpack = By.id("item_4_title_link");
    private final By bikeLight = By.id("item_0_title_link");
    private final By backButton = By.id("back-to-products");
    private final By productPage = By.className("inventory_list");
    private final By detailsAddToCartButton = By.id("add-to-cart");
    private final By detailsRemoveButton = By.id("remove");

    public ItemsPage(WebDriver driver) {
        super(driver);
    }


    // =========================
    // Locator Getters
    // =========================

    public By getProductsLocator() {
        return products;
    }

    public By getProductNamesLocator() {
        return productNames;
    }

    public By getAddToCartButtonsLocator() {
        return addToCartButtons;
    }

    public By getRemoveButtonsLocator() {
        return removeButtons;
    }

    public By getCartBadgeLocator() {
        return cartBadge;
    }

    public By getBackpackLocator() {
        return backpack;
    }

    public By getBikeLightLocator() {
        return bikeLight;
    }

    public By getBackButtonLocator() {
        return backButton;
    }

    public By getProductPageLocator() {
        return productPage;
    }

    public WebElement getDetailsAddToCartButton() {
        return findElement(detailsAddToCartButton);
    }

    public WebElement getDetailsRemoveButton() {
        return findElement(detailsRemoveButton);
    }


    public List<WebElement> getProducts() {
        log.info("Getting product word at inventory page");
        return findElements(getProductsLocator());
    }

    public List<WebElement> getAddToCartButtons() {
        log.info("Getting add to cart button's locator from inventory page");
        return findElements(getAddToCartButtonsLocator());
    }

    public List<WebElement> getRemoveButtons() {
        log.info("Getting remove button's locator from inventory page");
        return findElements(getRemoveButtonsLocator());
    }

    public WebElement getBackPackElement() {
        log.info("Getting BackPack element link");
        return findElement(getBackpackLocator());
    }

    public WebElement getBackLightElement() {
        log.info("Getting BackLight element link");
        return findElement(getBikeLightLocator());
    }

    public WebElement getBackButton() {
        log.info("Getting back button's locator to back inventory page");
        return findElement(getBackButtonLocator());
    }

    public WebElement getProductPage() {
        log.info("Getting inventory page");
        return findElement(getProductPageLocator());
    }


    public List<WebElement> getCartBadge() {
        log.info("Getting cart badge locators");
        return findElements(getCartBadgeLocator());
    }
}