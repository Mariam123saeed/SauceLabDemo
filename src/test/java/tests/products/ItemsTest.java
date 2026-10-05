package tests.products;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pages.products.HomePage;
import org.example.pages.products.ItemsPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class ItemsTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(ItemsTest.class);

    ItemsPage itemPage;
    HomePage homePage;

    SoftAssert softAssert;


    @BeforeMethod
    public void setUpDetailPage() {

        log.info("========== ItemsTest Setup Started ==========");

        log.info("Entering valid username");
        loginPage.enterUsername("standard_user");

        log.info("Entering valid password");
        loginPage.enterPassword("secret_sauce");

        log.info("Clicking Login button");
        loginPage.clickLogin();

        log.info("Initializing HomePage and ItemsPage");
        itemPage = new ItemsPage(driver);
        homePage = new HomePage(driver);
        softAssert = new SoftAssert();

        log.info("ItemsTest setup completed successfully");
    }


    // =========================================================
    // Add all products then verify Remove from Product Details
    // =========================================================

    @Test
    public void addAllProductsThenVerifyAndRemoveFromDetails() {

        log.info("========== Add All Products Test Started ==========");

        log.info("Getting product names before changing the buttons");

        List<String> productNames = new ArrayList<>();

        for (WebElement productName : homePage.getProductNames()) {
            productNames.add(productName.getText().trim());
        }

        log.info("Total products found: {}", productNames.size());


        // Add all products
        log.info("Adding all products to cart");

        while (!homePage.getAddToCartButtons().isEmpty()) {
            homePage.getAddToCartButtons().get(0).click();
        }

        log.info("All available products were added to cart");


        // Verify all products were added
        log.info("Verifying all products were added successfully");

        softAssert.assertEquals(
                itemPage.getRemoveButtons().size(),
                productNames.size(),
                "Not all products were added to cart"
        );

        log.info(
                "Remove buttons count: {}, Expected: {}",
                itemPage.getRemoveButtons().size(),
                productNames.size()
        );


        // Open each product
        for (String productName : productNames) {

            log.info("Opening product details: {}", productName);

            for (WebElement product : itemPage.getProducts()) {

                String currentProductName =
                        product.findElement(itemPage.getProductNamesLocator())
                                .getText()
                                .trim();

                if (currentProductName.equals(productName)) {

                    log.info("Product found: {}", productName);

                    product.findElement(itemPage.getProductNamesLocator()).click();

                    log.info("Product details page opened: {}", productName);

                    break;
                }
            }


            // Verify Remove
            log.info("Verifying Remove button for: {}", productName);

            softAssert.assertEquals(
                    itemPage.getDetailsRemoveButton().getText(),
                    "Remove",
                    "Button should be Remove for: " + productName
            );

            log.info("Remove button verified for: {}", productName);


            // Remove product
            log.info("Removing product from cart: {}", productName);

            itemPage.getDetailsRemoveButton().click();

            log.info("Product removed: {}", productName);


            // Verify Add to cart
            log.info("Verifying Add to cart button after removal");

            softAssert.assertEquals(
                    itemPage.getDetailsAddToCartButton().getText(),
                    "Add to cart",
                    "Button should return to Add to cart for: " + productName
            );

            log.info("Add to cart button verified for: {}", productName);


            // Back to inventory
            log.info("Returning to Products page");

            itemPage.getBackButton().click();

            Assert.assertTrue(
                    driver.getCurrentUrl().contains("saucedemo.com"),
                    "User should remain on login page"
            );

            log.info("Successfully returned to Products page");
        }

        log.info("========== Add All Products Test Completed ==========");
    }


    // =========================================================
    // Verify Item Details
    // =========================================================

    @Test
    public void verifyItemDetails() {

        log.info("========== Verify Item Details Test Started ==========");

        log.info("Verifying Bike Light is displayed");

        softAssert.assertTrue(
                itemPage.getBackLightElement().isDisplayed(),
                "Bike Light is not displayed"
        );

        log.info("Bike Light displayed successfully");

        log.info("Verifying Backpack is displayed");

        softAssert.assertTrue(
                itemPage.getBackPackElement().isDisplayed(),
                "Backpack is not displayed"
        );

        log.info("Backpack displayed successfully");

        log.info("========== Verify Item Details Test Completed ==========");
    }


    // =========================================================
    // Verify Add to Cart Button
    // =========================================================

    @Test
    public void verifyAddToCartButton() {

        log.info("========== Verify Add to Cart Button Test Started ==========");

        log.info("Getting first Add to Cart button");

        WebElement addButton = itemPage.getAddToCartButtons().get(0);

        log.info("Verifying Add to Cart button is displayed");

        softAssert.assertTrue(
                addButton.isDisplayed(),
                "Add to Cart button is not displayed"
        );

        log.info("Add to Cart button is displayed");

        log.info("Clicking Add to Cart button");

        addButton.click();

        log.info("Verifying Remove button appears after adding product");

        softAssert.assertEquals(
                itemPage.getRemoveButtons().size(),
                1,
                "Remove button should appear after adding product"
        );

        log.info(
                "Remove buttons count after adding product: {}",
                itemPage.getRemoveButtons().size()
        );

        log.info("Verifying button text changed to Remove");

        softAssert.assertEquals(
                itemPage.getRemoveButtons().get(0).getText(),
                "Remove",
                "Add to Cart did not change to Remove"
        );

        log.info("Add to Cart button changed to Remove successfully");

        log.info("========== Verify Add to Cart Button Test Completed ==========");
    }


    // =========================================================
    // Back To Products Without Adding
    // =========================================================

    @Test
    public void backToProductsWithoutAdding() {

        log.info("========== Back To Products Without Adding Test Started ==========");

        log.info("Opening Bike Light product");

        itemPage.getBackLightElement().click();

        log.info("Clicking Back to Products button");

        itemPage.getBackButton().click();

        log.info("Verifying user returned to Products page");

        softAssert.assertTrue(
                itemPage.getProductPage().isDisplayed(),
                "Failed to return to products page"
        );

        log.info("Successfully returned to Products page");

        log.info("========== Back To Products Without Adding Test Completed ==========");
    }


    // =========================================================
    // Back To Products After Opening Item
    // =========================================================

    @Test
    public void backToProductsAfterOpeningItem() {

        log.info("========== Back To Products After Opening Item Test Started ==========");

        log.info("Opening Backpack product");

        itemPage.getBackPackElement().click();

        log.info("Clicking Back to Products button");

        itemPage.getBackButton().click();

        log.info("Verifying user returned to Products page");

        softAssert.assertTrue(
                itemPage.getProductPage().isDisplayed(),
                "Failed to return to products page"
        );

        log.info("Successfully returned to Products page");

        log.info("========== Back To Products After Opening Item Test Completed ==========");
    }


    // =========================================================
    // Verify Item Added To Cart
    // =========================================================

    @Test
    public void verifyItemAddedToCart() {

        log.info("========== Verify Item Added To Cart Test Started ==========");

        log.info("Adding first product to cart");

        itemPage.getAddToCartButtons().get(0).click();

        log.info("Verifying cart badge is displayed");

        softAssert.assertFalse(
                itemPage.getCartBadge().isEmpty(),
                "Cart badge should be displayed"
        );

        log.info(
                "Cart badge elements found: {}",
                itemPage.getCartBadge().size()
        );


        if (!itemPage.getCartBadge().isEmpty()) {

            log.info("Verifying cart count");

            softAssert.assertEquals(
                    itemPage.getCartBadge().get(0).getText(),
                    "1",
                    "Cart count is incorrect"
            );

            log.info(
                    "Cart count verified successfully: {}",
                    itemPage.getCartBadge().get(0).getText()
            );
        }

        log.info("========== Verify Item Added To Cart Test Completed ==========");
    }
}
