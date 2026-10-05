package tests.cart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pages.cart.CartPage;
import org.example.pages.cart.CheckOutPage;
import org.example.pages.products.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

public class CheckOutTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(CheckOutTest.class);

    HomePage homePage;
    CartPage cartPage;
    CheckOutPage checkOutPage;


    // =========================================
    // Setup
    // =========================================

    @BeforeMethod
    public void checkoutSetUp() {

        log.info("========== CheckOutTest Setup Started ==========");

        log.info("Logging in with configured credentials");

        loginPage.login(
                configHandler.getValue("username"),
                configHandler.getValue("password")
        );

        log.info("Login completed successfully");

        log.info("Initializing HomePage, CartPage and CheckOutPage");

        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);
        checkOutPage = new CheckOutPage(driver);

        log.info("CheckOutTest setup completed successfully");
    }


    // =========================================
    // Test 1 - Checkout Information Fields
    // =========================================

    @Test
    public void verifyCheckoutInformationFields() {

        log.info("========== TC_CHECKOUT_001 Checkout Information Fields Started ==========");

        log.info("Adding first product to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Product added successfully");

        log.info("Opening cart");

        homePage.getCartIcon().click();

        log.info("Cart opened successfully");

        log.info("Clicking Checkout button");

        cartPage.getCheckoutButton().click();

        log.info("Checkout information page opened");

        log.info("Verifying First Name field");

        Assert.assertTrue(
                checkOutPage.getFirstName().isDisplayed(),
                "First Name field should be displayed"
        );

        log.info("First Name field is displayed");

        log.info("Verifying Last Name field");

        Assert.assertTrue(
                checkOutPage.getLastName().isDisplayed(),
                "Last Name field should be displayed"
        );

        log.info("Last Name field is displayed");
        log.info("Verifying Postal Code field");

        Assert.assertTrue(
                checkOutPage.getPostalCode().isDisplayed(),
                "Postal Code field should be displayed"
        );

        log.info("Postal Code field is displayed");

        log.info("Verifying Continue button");

        Assert.assertTrue(
                checkOutPage.getContinueButton().isDisplayed(),
                "Continue button should be displayed"
        );

        log.info("Continue button is displayed");

        log.info("Verifying Cancel button");

        Assert.assertTrue(
                checkOutPage.getCancelButton().isDisplayed(),
                "Cancel button should be displayed"
        );

        log.info("Cancel button is displayed");

        log.info("========== TC_CHECKOUT_001 Completed ==========");
    }


    // =========================================
    // Test 2 - Enter Customer Information
    // =========================================

    @Test
    public void enterCustomerInformation() {

        log.info("========== TC_CHECKOUT_002 Enter Customer Information Started ==========");

        log.info("Adding first product to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Product added successfully");

        log.info("Opening cart");

        homePage.getCartIcon().click();

        log.info("Clicking Checkout button");

        cartPage.getCheckoutButton().click();

        log.info("Checkout information page opened");

        log.info("Entering customer information");

        checkOutPage.fillCustomerInformation(
                "Mariam",
                "Saeed",
                "12345"
        );

        log.info("Customer information entered successfully");

        log.info("Verifying First Name value");

        Assert.assertEquals(
                checkOutPage.getFirstName().getAttribute("value"),
                "Mariam"
        );

        log.info("First Name verified successfully");

        log.info("Verifying Last Name value");

        Assert.assertEquals(
                checkOutPage.getLastName().getAttribute("value"),
                "Saeed"
        );

        log.info("Last Name verified successfully");

        log.info("Verifying Postal Code value");

        Assert.assertEquals(
                checkOutPage.getPostalCode().getAttribute("value"),
                "12345"
        );

        log.info("Postal Code verified successfully");

        log.info("========== TC_CHECKOUT_002 Completed ==========");
    }


    // =========================================
    // Test 3 - Continue To Overview
    // =========================================

    @Test
    public void continueToOverview() {

        log.info("========== TC_CHECKOUT_003 Continue To Overview Started ==========");

        log.info("Adding first product to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Product added successfully");

        log.info("Opening cart");

        homePage.getCartIcon().click();

        log.info("Cart opened successfully");

        log.info("Clicking Checkout button");

        cartPage.getCheckoutButton().click();

        log.info("Checkout information page opened");

        log.info("Entering customer information and continuing to overview");

        checkOutPage.continueToOverview(
                "Mariam",
                "Saeed",
                "12345"
        );

        log.info("Checkout overview page opened successfully");

        log.info("Verifying subtotal");

        Assert.assertTrue(
                checkOutPage.getSubtotal()
                        .getText()
                        .contains("Item total"),
                "Subtotal should be displayed"
        );

        log.info("Subtotal is displayed");

        log.info("Verifying tax");

        Assert.assertTrue(
                checkOutPage.getTax().isDisplayed(),
                "Tax should be displayed"
        );

        log.info("Tax is displayed");

        log.info("Verifying total");

        Assert.assertTrue(
                checkOutPage.getTotal().isDisplayed(),
                "Total should be displayed"
        );

        log.info("Total is displayed");

        log.info("Verifying Finish button");

        Assert.assertTrue(
                checkOutPage.getFinishButton().isDisplayed(),
                "Finish button should be displayed"
        );

        log.info("Finish button is displayed");

        log.info("========== TC_CHECKOUT_003 Completed ==========");
    }


    // =========================================
    // Test 4 - Verify Checkout Total For 3 Products
    // =========================================

    @Test
    public void verifyCheckoutTotalForThreeProducts() {

        log.info("========== TC_CHECKOUT_004 Verify Checkout Total Started ==========");

        double expectedSubtotal = 0.0;

        log.info("Adding 3 products and calculating expected subtotal");

        for (int i = 0; i < 3; i++) {

            String productName =
                    homePage.getProductNames()
                            .get(i)
                            .getText();

            String productPrice =
                    homePage.getProductPrices()
                            .get(i)
                            .getText();

            double price =
                    Double.parseDouble(
                            productPrice.replace("$", "")
                    );

            expectedSubtotal += price;

            log.info(
                    "Product {} - Name: {} | Price: ${}",
                    i + 1,
                    productName,
                    price
            );

            homePage.getAddToCartButtons()
                    .get(0)
                    .click();

            log.info("Product added successfully: {}", productName);
        }

        log.info(
                "Expected subtotal for 3 products: ${}",
                expectedSubtotal
        );


        log.info("Opening cart");

        homePage.getCartIcon().click();

        log.info("Verifying cart contains 3 products");

        Assert.assertEquals(
                cartPage.getCartItems().size(),
                3,
                "Cart should contain 3 products"
        );

        log.info("Cart contains 3 products successfully");

        log.info("Clicking Checkout button");

        cartPage.getCheckoutButton().click();

        log.info("Entering customer information and continuing to overview");

        checkOutPage.continueToOverview(
                "Mariam",
                "Saeed",
                "12345"
        );

        log.info("Checkout overview page opened");


        log.info("Getting actual subtotal, tax and total");

        double actualSubtotal =
                checkOutPage.getSubtotalValue();

        double actualTax =
                checkOutPage.getTaxValue();

        double actualTotal =
                checkOutPage.getTotalValue();

        log.info("Actual subtotal: ${}", actualSubtotal);
        log.info("Actual tax: ${}", actualTax);
        log.info("Actual total: ${}", actualTotal);


        double expectedTax =
                Math.round(
                        expectedSubtotal * 0.08 * 100.0
                ) / 100.0;

        double expectedTotal =
                Math.round(
                        (expectedSubtotal + expectedTax) * 100.0
                ) / 100.0;

        log.info("Expected tax: ${}", expectedTax);
        log.info("Expected total: ${}", expectedTotal);


        log.info("Verifying subtotal");

        Assert.assertEquals(
                actualSubtotal,
                expectedSubtotal,
                0.01,
                "Subtotal is incorrect"
        );

        log.info("Subtotal verified successfully");


        log.info("Verifying tax");

        Assert.assertEquals(
                actualTax,
                expectedTax,
                0.01,
                "Tax is incorrect"
        );

        log.info("Tax verified successfully");


        log.info("Verifying total");

        Assert.assertEquals(
                actualTotal,
                expectedTotal,
                0.01,
                "Total is incorrect"
        );

        log.info("Total verified successfully");

        log.info("========== TC_CHECKOUT_004 Completed ==========");
    }


    // =========================================
    // Test 5 - Verify Order Completion
    // =========================================

    @Test
    public void verifyOrderCompletion() {

        log.info("========== TC_CHECKOUT_005 Verify Order Completion Started ==========");

        log.info("Adding first product to cart");

        homePage.getAddToCartButtons()
                .get(0)
                .click();

        log.info("Product added successfully");

        log.info("Opening cart");

        homePage.getCartIcon().click();

        log.info("Cart opened successfully");

        log.info("Clicking Checkout button");

        cartPage.getCheckoutButton().click();

        log.info("Checkout information page opened");

        log.info("Entering customer information and continuing to overview");

        checkOutPage.continueToOverview(
                "Mariam",
                "Saeed",
                "12345"
        );

        log.info("Checkout overview page opened");

        log.info("Clicking Finish button");

        checkOutPage.clickFinish();

        log.info("Finish button clicked");

        log.info("Verifying order completion header");

        Assert.assertEquals(
                checkOutPage.getCompleteHeaderText(),
                "Thank you for your order!",
                "Order completion message is incorrect"
        );

        log.info("Order completion header verified successfully");

        log.info("Verifying order completion description");

        Assert.assertTrue(
                checkOutPage.getCompleteTextValue()
                        .contains("Your order has been dispatched"),
                "Order completion description is incorrect"
        );

        log.info("Order completion description verified successfully");

        log.info("Verifying Back Home button");

        Assert.assertTrue(
                checkOutPage.getBackHomeButton().isDisplayed(),
                "Back Home button should be displayed"
        );

        log.info("Back Home button is displayed");

        log.info("Verifying Generate PDF button");

        Assert.assertTrue(
                checkOutPage.getGeneratePdfButton().isDisplayed(),
                "Generate PDF button should be displayed"
        );

        log.info("Generate PDF button is displayed");

        log.info("========== TC_CHECKOUT_005 Completed ==========");
    }
}
