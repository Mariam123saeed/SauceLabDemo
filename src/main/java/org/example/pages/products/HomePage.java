package org.example.pages.products;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends BasePage {

    private Logger log = LogManager.getLogger(HomePage.class);

    private final By products = By.className("inventory_item");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By cartCount = By.className("shopping_cart_badge");
    private final By addToCartButtons = By.cssSelector("button[id^='add-to-cart-']");
    private final By removeFromCartButtons = By.cssSelector("button[id^='remove-']");
    private final By productSortList = By.className("product_sort_container");




    public HomePage(WebDriver driver) {
        super(driver);
    }


    public By getProductsLocator() {
        return products;
    }

    public By getProductNamesLocator() {
        return productNames;
    }

    public By getProductPricesLocator() {
        return productPrices;
    }

    public By getCartIconLocator() {
        return cartIcon;
    }

    public By getCartCountLocator() {
        return cartCount;
    }

    public By getAddToCartButtonsLocator() {
        return addToCartButtons;
    }

    public By getRemoveFromCartButtonsLocator() {
        return removeFromCartButtons;
    }

    public By getProductSortListLocator() {
        return productSortList;
    }




    public List<WebElement> getProducts() {
        log.info("Getting inventory page");
        return findElements(getProductsLocator());
    }

    public List<WebElement> getProductNames() {
        log.info("Getting products' names");
        return findElements(getProductNamesLocator());
    }

    public List<WebElement> getProductPrices() {
        log.info("Getting products' prices");
        return findElements(getProductPricesLocator());
    }

    public List<WebElement> getAddToCartButtons() {
        log.info("Getting add to cart button locators");
        return findElements(getAddToCartButtonsLocator());
    }

    public List<WebElement> getRemoveFromCartButtons() {
        log.info("Getting remove from cart button locators");
        return findElements(getRemoveFromCartButtonsLocator());
    }

    public WebElement getProductSortList() {
        log.info("Getting sort list locator");
        return findElement(getProductSortListLocator());
    }

    public List<WebElement> getCartCount() {
        log.info("Getting cart badge locators");
        return findElements(getCartCountLocator());
    }

    public WebElement getCartIcon() {
        log.info("Getting cart icon ");
        return findElement(getCartIconLocator());
    }
}