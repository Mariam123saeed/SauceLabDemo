package tests.cart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pages.cart.CartPage;
import org.example.pages.products.HomePage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

import java.util.List;

public class CartTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(CartTest.class);

    HomePage homePage;
    CartPage cartPage;
    SoftAssert softAssert;


    // =========================================================
    // Setup
    // =========================================================

    @BeforeMethod
    public void setUpCart() {

        log.info("========== CartTest Setup Started ==========");

        log.info("Entering valid username");
        loginPage.enterUsername("standard_user");

        log.info("Entering valid password");
        loginPage.enterPassword("secret_sauce");

        log.info("Clicking Login button");
        loginPage.clickLogin();

        log.info("Initializing HomePage and CartPage");
        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);

        softAssert = new SoftAssert();

        log.info("CartTest setup completed successfully");
    }


    // =========================================================
    // TC01 - Add single product to cart
    // =========================================================

    @Test
    public void addSingleProductToCart() {

        log.info("========== TC01 Add Single Product Started ==========");

        log.info("Adding Backpack to cart");

        cartPage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Backpack added to cart");
        log.info("Verifying cart badge");

        List<WebElement> cartBadge = cartPage.getCartBadge();

        softAssert.assertFalse(
                cartBadge.isEmpty(),
                "Cart badge should be displayed"
        );

        if (!cartBadge.isEmpty()) {

            log.info("Cart badge displayed with count: {}",
                    cartBadge.get(0).getText());

            softAssert.assertEquals(
                    cartBadge.get(0).getText(),
                    "1",
                    "Cart count is incorrect"
            );
        }

        log.info("Verifying cart count completed");

        softAssert.assertAll();

        log.info("========== TC01 Completed ==========");
    }


    // =========================================================
    // TC02 - Add multiple products
    // =========================================================

    @Test
    public void addMultipleProductsToCart() {

        log.info("========== TC02 Add Multiple Products Started ==========");

        log.info("Adding Backpack to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Backpack added successfully");

        log.info("Adding Bike Light to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Bike Light added successfully");

        log.info("Verifying cart badge");

        List<WebElement> cartBadge = cartPage.getCartBadge();

        softAssert.assertFalse(
                cartBadge.isEmpty(),
                "Cart badge should be displayed"
        );

        if (!cartBadge.isEmpty()) {

            log.info("Cart count: {}", cartBadge.get(0).getText());

            softAssert.assertEquals(
                    cartBadge.get(0).getText(),
                    "2",
                    "Cart count is incorrect"
            );
        }

        softAssert.assertAll();

        log.info("========== TC02 Completed ==========");
    }


    // =========================================================
    // TC03 - Remove product from cart
    // =========================================================

    @Test
    public void removeProductFromCart() {

        log.info("========== TC03 Remove Product Started ==========");

        log.info("Adding first product to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Adding second product to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Two products added successfully");

        log.info("Opening cart");

        cartPage.getCartIcon().click();

        log.info("Cart page opened");

        log.info("Removing first product");

        cartPage.getRemoveButtons()
                .get(0)
                .click();

        log.info("First product removed");

        log.info("Verifying one product remains in cart");

        softAssert.assertEquals(
                cartPage.getCartItems().size(),
                1,
                "Product was not removed"
        );

        softAssert.assertAll();

        log.info("========== TC03 Completed ==========");
    }


    // =========================================================
    // TC04 - Verify button changes to Remove
    // =========================================================

    @Test
    public void verifyButtonChangeAfterAdding() {

        log.info("========== TC04 Verify Button Change Started ==========");

        log.info("Adding Backpack to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Backpack added successfully");

        log.info("Getting Remove buttons");

        List<WebElement> removeButtons =
                cartPage.getRemoveButtons();

        log.info("Remove buttons found: {}", removeButtons.size());

        softAssert.assertEquals(
                removeButtons.size(),
                1,
                "Remove button should be displayed"
        );

        if (!removeButtons.isEmpty()) {

            log.info("Verifying button text is Remove");

            softAssert.assertEquals(
                    removeButtons.get(0).getText(),
                    "Remove",
                    "Button did not change to Remove"
            );

            log.info("Button changed to Remove successfully");
        }

        softAssert.assertAll();

        log.info("========== TC04 Completed ==========");
    }


    // =========================================================
    // TC05 - Verify cart icon updates count
    // =========================================================

    @Test
    public void verifyCartIconUpdatesCount() {

        log.info("========== TC05 Verify Cart Icon Count Started ==========");

        log.info("Adding Backpack to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Backpack added");

        List<WebElement> cartBadge =
                cartPage.getCartBadge();

        log.info("Verifying cart badge after adding first product");

        softAssert.assertFalse(
                cartBadge.isEmpty(),
                "Cart badge should be displayed"
        );

        if (!cartBadge.isEmpty()) {

            log.info("Cart count after first product: {}",
                    cartBadge.get(0).getText());

            softAssert.assertEquals(
                    cartBadge.get(0).getText(),
                    "1",
                    "Cart count should be 1"
            );
        }


        // Add Bike Light

        log.info("Adding Bike Light to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Bike Light added");

        cartBadge = cartPage.getCartBadge();

        log.info("Verifying cart count is 2");

        softAssert.assertEquals(
                cartBadge.get(0).getText(),
                "2",
                "Cart count should be 2"
        );


        // Remove Backpack

        log.info("Removing Backpack from cart");

        cartPage.getRemoveButtons()
                .get(0)
                .click();

        log.info("Backpack removed");

        cartBadge = cartPage.getCartBadge();

        log.info("Verifying cart count decreased to 1");

        softAssert.assertEquals(
                cartBadge.get(0).getText(),
                "1",
                "Cart count should be 1 after removing product"
        );

        softAssert.assertAll();

        log.info("========== TC05 Completed ==========");
    }


    // =========================================================
    // TC06 - Add product then checkout
    // =========================================================

    @Test
    public void addProductThenCheckout() {

        log.info("========== TC06 Add Product Then Checkout Started ==========");

        log.info("Adding product to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Product added successfully");

        log.info("Opening cart");

        cartPage.getCartIcon().click();

        log.info("Cart page opened");

        log.info("Clicking Checkout button");

        cartPage.getCheckoutButton().click();

        log.info("Checkout button clicked");

        log.info("Verifying checkout page");

        softAssert.assertTrue(
                driver.getCurrentUrl()
                        .contains("checkout-step-one"),
                "Checkout page was not opened"
        );

        log.info("Checkout page verified successfully");

        softAssert.assertAll();

        log.info("========== TC06 Completed ==========");
    }


    // =========================================================
    // TC07 - Verify cart UI elements
    // =========================================================

    @Test
    public void verifyCartUIElements() {

        log.info("========== TC07 Verify Cart UI Started ==========");

        log.info("Opening cart");

        cartPage.getCartIcon().click();

        log.info("Cart page opened");

        log.info("Verifying Cart list");

        softAssert.assertTrue(
                cartPage.getCartList().isDisplayed(),
                "Cart list is not displayed"
        );

        log.info("Cart list verified");

        log.info("Verifying Continue Shopping button");

        softAssert.assertTrue(
                cartPage.getContinueShoppingButton()
                        .isDisplayed(),
                "Continue Shopping button is not displayed"
        );

        log.info("Continue Shopping button verified");

        log.info("Verifying Checkout button");

        softAssert.assertTrue(
                cartPage.getCheckoutButton()
                        .isDisplayed(),
                "Checkout button is not displayed"
        );

        log.info("Checkout button verified");

        softAssert.assertAll();

        log.info("========== TC07 Completed ==========");
    }


    // =========================================================
    // TC08 - Add all available products
    // =========================================================

    @Test
    public void addMaximumProducts() {

        log.info("========== TC08 Add Maximum Products Started ==========");

        log.info("Adding all available products");

        for (int i = 0; i < 6; i++) {

            log.info("Adding product {} of 6", i + 1);

            cartPage.getAddToCartButtons()
                    .get(0)
                    .click();
        }

        log.info("All 6 products were added");

        log.info("Verifying cart count");

        List<WebElement> cartBadge =
                cartPage.getCartBadge();

        softAssert.assertFalse(
                cartBadge.isEmpty(),
                "Cart badge should be displayed"
        );

        if (!cartBadge.isEmpty()) {

            log.info("Cart count: {}", cartBadge.get(0).getText());

            softAssert.assertEquals(
                    cartBadge.get(0).getText(),
                    "6",
                    "Not all products were added"
            );
        }

        softAssert.assertAll();

        log.info("========== TC08 Completed ==========");
    }


    // =========================================================
    // TC09 - Continue Shopping
    // =========================================================

    @Test
    public void continueShopping() {

        log.info("========== TC09 Continue Shopping Started ==========");

        log.info("Adding product to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Product added successfully");

        log.info("Opening cart");

        cartPage.getCartIcon().click();

        log.info("Cart page opened");

        log.info("Clicking Continue Shopping");

        cartPage.getContinueShoppingButton()
                .click();

        log.info("Continue Shopping clicked");

        log.info("Verifying user returned to Products page");

        softAssert.assertTrue(
                driver.getCurrentUrl()
                        .contains("inventory.html"),
                "User was not returned to products page"
        );

        log.info("User returned to Products page successfully");

        softAssert.assertAll();

        log.info("========== TC09 Completed ==========");
    }


    // =========================================================
    // TC10 - Verify cart item name
    // =========================================================

    @Test
    public void verifyCartItemName() {

        log.info("========== TC10 Verify Cart Item Name Started ==========");

        log.info("Adding Backpack to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Backpack added successfully");

        log.info("Opening cart");

        cartPage.getCartIcon().click();

        log.info("Cart page opened");

        log.info("Getting cart item names");

        List<WebElement> itemNames =
                cartPage.getCartItemNames();

        log.info("Cart items found: {}", itemNames.size());

        softAssert.assertEquals(
                itemNames.size(),
                1,
                "Cart should contain one product"
        );

        if (!itemNames.isEmpty()) {

            log.info("Verifying product name");

            softAssert.assertEquals(
                    itemNames.get(0).getText(),
                    "Sauce Labs Backpack",
                    "Incorrect product name in cart"
            );

            log.info("Product name verified: {}",
                    itemNames.get(0).getText());
        }

        softAssert.assertAll();

        log.info("========== TC10 Completed ==========");
    }


    // =========================================================
    // TC11 - Remove all products
    // =========================================================

    @Test
    public void removeAllProducts() {

        log.info("========== TC11 Remove All Products Started ==========");

        log.info("Adding all products from inventory");

        while (!homePage.getAddToCartButtons().isEmpty()) {

            log.info("Adding next available product");

            homePage.getAddToCartButtons()
                    .get(0)
                    .click();
        }

        log.info("All available products were added");

        log.info("Verifying all 6 products were added");

        softAssert.assertEquals(
                homePage.getRemoveFromCartButtons().size(),
                6,
                "All products should be added"
        );

        log.info(
                "Products added: {}",
                homePage.getRemoveFromCartButtons().size()
        );


        // Open Cart

        log.info("Opening cart");

        homePage.getCartIcon().click();

        log.info("Cart page opened");

        log.info("Verifying cart contains 6 products");

        softAssert.assertEquals(
                cartPage.getCartItems().size(),
                6,
                "Cart should contain 6 products"
        );

        log.info(
                "Cart items found: {}",
                cartPage.getCartItems().size()
        );


        // Remove all products

        log.info("Removing all products from cart");

        while (!cartPage.getRemoveButtons().isEmpty()) {

            log.info("Removing next product");

            cartPage.getRemoveButtons()
                    .get(0)
                    .click();
        }

        log.info("All products removed");


        // Verify cart is empty

        log.info("Verifying cart is empty");

        softAssert.assertTrue(
                cartPage.getCartItems().isEmpty(),
                "Cart should be empty"
        );

        log.info("Cart items verified as empty");

        softAssert.assertTrue(
                cartPage.getRemoveButtons().isEmpty(),
                "Remove buttons should not exist"
        );

        log.info("Remove buttons verified as absent");

        softAssert.assertTrue(
                cartPage.getCartBadge().isEmpty(),
                "Cart badge should disappear"
        );

        log.info("Cart badge verified as absent");

        softAssert.assertAll();

        log.info("========== TC11 Completed ==========");
    }
}
