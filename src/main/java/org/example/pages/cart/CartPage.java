package org.example.pages.cart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private Logger log = LogManager.getLogger(CartPage.class);

    // =========================================================
    // Locators
    // =========================================================

    private final By cartItems = By.className("cart_item");
    private final By cartItemNames = By.className("inventory_item_name");
    private final By removeButtons = By.cssSelector("button[id^='remove-']");
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartList = By.className("cart_list");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By addToCartButtons = By.cssSelector("button[id^='add-to-cart-']");

    // =========================================================
    // Constructor
    // =========================================================

    public CartPage(WebDriver driver) {
        super(driver);
    }


    // =========================================================
    // Locator Getters
    // =========================================================

    public By getCartItemsLocator() {
        return cartItems;
    }

    public By getCartItemNamesLocator() {
        return cartItemNames;
    }

    public By getRemoveButtonsLocator() {
        return removeButtons;
    }

    public By getCheckoutButtonLocator() {
        return checkoutButton;
    }

    public By getContinueShoppingButtonLocator() {
        return continueShoppingButton;
    }

    public By getCartBadgeLocator() {
        return cartBadge;
    }

    public By getCartListLocator() {
        return cartList;
    }

    public By getCartIconLocator() {
        return cartIcon;
    }

    public By getAddToCartButtonsLocator() {
        return addToCartButtons;
    }


    // =========================================================
    // WebElement Getters
    // =========================================================

    public List<WebElement> getCartItems() {
        log.info("Getting cart item page");
        return findElements(getCartItemsLocator());
    }

    public List<WebElement> getCartItemNames() {
        log.info("Getting items' names at cart");
        return findElements(getCartItemNamesLocator());
    }

    public List<WebElement> getRemoveButtons() {
        log.info("Getting remove from cart button locators");
        return findElements(getRemoveButtonsLocator());
    }

    public WebElement getCheckoutButton() {
        log.info("Getting checkout button locator");
        return findElement(getCheckoutButtonLocator());
    }

    public WebElement getContinueShoppingButton() {
        log.info("Getting continue shopping button locator");
        return findElement(getContinueShoppingButtonLocator());
    }

    public List<WebElement> getCartBadge() {
        log.info("Getting cart badge locators");
        return findElements(getCartBadgeLocator());
    }

    public WebElement getCartList() {
        log.info("Getting cart list locator ");
        return findElement(getCartListLocator());
    }

    public WebElement getCartIcon() {
        log.info("Getting cart icon ");
        return findElement(getCartIconLocator());
    }

    public List<WebElement> getAddToCartButtons() {
        log.info("Getting add to cart button locators");
        return findElements(getAddToCartButtonsLocator());
    }
}