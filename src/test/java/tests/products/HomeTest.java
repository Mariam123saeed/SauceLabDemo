package tests.products;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pages.products.HomePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;
import tests.dataProviderTest.DataProviderTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(HomeTest.class);

    HomePage homePage;
    SoftAssert softAssert;


    // =========================================================
    // Setup
    // =========================================================

    @BeforeMethod
    public void setUpHomePage() {

        log.info("========== Home Page Test Started ==========");

        log.info("Logging in with standard user");

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        log.info("Login completed successfully");

        homePage = new HomePage(driver);
        softAssert = new SoftAssert();

        log.info("HomePage object initialized");
    }

    // =========================================================
    // Verify Products Count
    // =========================================================

    @Test
    public void verifyProductsCount() {

        log.info("========== TC_PRODUCTS_001 Started ==========");

        log.info("Getting products count");

        int actualCount = homePage.getProducts().size();

        log.info("Actual products count: {}", actualCount);

        softAssert.assertEquals(
                actualCount,
                6,
                "Products count is incorrect"
        );

        log.info("Expected products count: 6");
        log.info("========== TC_PRODUCTS_001 Completed ==========");
    }


    // =========================================================
    // Verify Product Names
    // =========================================================

    @Test(dataProvider = "productNames", dataProviderClass = DataProviderTest.class)
    public void verifyProductNames(int index, String expectedProductName) {

        log.info("========== TC_PRODUCTS_002 Started ==========");

        log.info("Verifying product at index {}. Expected name: {}", index, expectedProductName);

        List<String> actualProductNames = new ArrayList<>();

        for (WebElement product : homePage.getProductNames()) {

            actualProductNames.add(product.getText().trim());
        }

        log.info("Actual product names: {}", actualProductNames);

        String actualProductName = actualProductNames.get(index);

        log.info("Actual product name at index {}: {}", index, actualProductName);

        softAssert.assertEquals(
                actualProductName,
                expectedProductName,
                "Product name is not matching at index " + index
        );

        log.info(
                "Product name verified successfully: {}",
                expectedProductName
        );

        log.info("========== TC_PRODUCTS_002 Completed ==========");
    }


    // =========================================================
    // Add Product To Cart
    // =========================================================

    @Test
    public void addProductToCartTest() {

        log.info("========== TC_PRODUCTS_003 Started ==========");

        log.info("Adding first product to cart");

        WebElement addButton =
                homePage.getAddToCartButtons().getFirst();

        addButton.click();

        log.info("First product added successfully");

        int cartCount = Integer.parseInt(homePage.getCartCount().get(0).getText().trim());

        log.info("Cart count after adding product: {}", cartCount);

        softAssert.assertEquals(
                cartCount,
                1,
                "Cart count should be 1"
        );

        log.info("Checking Remove button");

        List<WebElement> removeButtons = homePage.getRemoveFromCartButtons();

        softAssert.assertEquals(
                removeButtons.size(),
                1,
                "Remove button should be displayed"
        );

        softAssert.assertEquals(
                removeButtons.get(0).getText(),
                "Remove",
                "Button text should change to Remove"
        );

        log.info("Remove button verified successfully");
        log.info("========== TC_PRODUCTS_003 Completed ==========");
    }


    // =========================================================
    // Add Multiple Products
    // =========================================================

    @Test
    public void addMultipleProductsTest() {

        log.info("========== TC_PRODUCTS_004 Started ==========");

        log.info("Adding first product");

        homePage.getAddToCartButtons().get(0).click();

        log.info("Adding second product");

        homePage.getAddToCartButtons().get(0).click();

        log.info("Adding third product");

        homePage.getAddToCartButtons().get(0).click();

        log.info("Three products added to cart");

        int cartCount =
                Integer.parseInt(
                        homePage.getCartCount()
                                .get(0)
                                .getText()
                                .trim()
                );

        log.info("Cart count: {}", cartCount);

        softAssert.assertEquals(
                cartCount,
                3,
                "Cart should contain 3 products"
        );

        int removeButtonsCount =
                homePage.getRemoveFromCartButtons().size();

        log.info(
                "Number of Remove buttons: {}",
                removeButtonsCount
        );

        softAssert.assertEquals(
                removeButtonsCount,
                3,
                "Three Remove buttons should be displayed"
        );

        log.info("========== TC_PRODUCTS_004 Completed ==========");
    }


    // =========================================================
    // Remove Product
    // =========================================================

    @Test
    public void removeProductTest() {

        log.info("========== TC_PRODUCTS_005 Started ==========");

        log.info("Adding first product to cart");

        homePage.getAddToCartButtons().get(0).click();

        log.info("Checking cart badge after adding product");

        softAssert.assertFalse(
                homePage.getCartCount().isEmpty(),
                "Cart badge should be displayed after adding a product"
        );

        if (!homePage.getCartCount().isEmpty()) {

            String cartCount =
                    homePage.getCartCount()
                            .get(0)
                            .getText();

            log.info("Cart count after adding product: {}", cartCount);

            softAssert.assertEquals(
                    cartCount,
                    "1",
                    "Cart count should be 1"
            );
        }

        log.info("Removing product from cart");

        homePage.getRemoveFromCartButtons().get(0).click();

        log.info("Checking cart badge after removing product");

        softAssert.assertTrue(
                homePage.getCartCount().isEmpty(),
                "Cart badge should disappear after removing the last product"
        );

        int addButtonsCount =
                homePage.getAddToCartButtons().size();

        log.info(
                "Number of Add to Cart buttons: {}",
                addButtonsCount
        );

        softAssert.assertEquals(
                addButtonsCount,
                6,
                "All Add to Cart buttons should be available"
        );

        log.info("Product removed successfully");

        log.info("========== TC_PRODUCTS_005 Completed ==========");
    }


    // =========================================================
    // Sort Price Low To High
    // =========================================================

    @Test
    public void sortProductsLowToHighTest() {

        log.info("========== TC_PRODUCTS_006 Started ==========");

        log.info("Selecting Price Low to High sorting");

        Select select =
                new Select(homePage.getProductSortList());

        select.selectByValue("lohi");

        log.info("Low to High sorting selected");

        Select updatedSelect =
                new Select(homePage.getProductSortList());

        softAssert.assertEquals(
                updatedSelect
                        .getFirstSelectedOption()
                        .getAttribute("value"),
                "lohi",
                "Low to High sorting was not selected"
        );

        log.info("Reading product prices");

        List<Double> actualPrices = new ArrayList<>();

        for (WebElement price : homePage.getProductPrices()) {

            String priceText =
                    price.getText()
                            .replace("$", "")
                            .trim();

            actualPrices.add(Double.parseDouble(priceText));
        }

        log.info("Actual prices: {}", actualPrices);

        List<Double> expectedPrices =
                new ArrayList<>(actualPrices);

        Collections.sort(expectedPrices);

        log.info("Expected sorted prices: {}", expectedPrices);

        softAssert.assertEquals(
                actualPrices,
                expectedPrices,
                "Products are not sorted from Low to High"
        );

        log.info("Low to High sorting verified successfully");

        log.info("========== TC_PRODUCTS_006 Completed ==========");
    }


    // =========================================================
    // Sort A To Z
    // =========================================================

    @Test
    public void sortProductsAToZTest() {

        log.info("========== TC_PRODUCTS_007 Started ==========");

        log.info("Selecting A to Z sorting");

        Select select =
                new Select(homePage.getProductSortList());

        select.selectByValue("az");

        log.info("A to Z sorting selected");

        List<String> actualNames = new ArrayList<>();

        for (WebElement name : homePage.getProductNames()) {

            actualNames.add(name.getText().trim());
        }

        log.info("Actual product order: {}", actualNames);

        List<String> expectedNames =
                new ArrayList<>(actualNames);

        Collections.sort(expectedNames);

        log.info("Expected A to Z order: {}", expectedNames);

        softAssert.assertEquals(
                actualNames,
                expectedNames,
                "Products are not sorted A to Z"
        );

        softAssert.assertEquals(
                select.getFirstSelectedOption()
                        .getAttribute("value"),
                "az",
                "Incorrect sorting option selected"
        );

        log.info("A to Z sorting verified successfully");

        log.info("========== TC_PRODUCTS_007 Completed ==========");
    }


    // =========================================================
    // Sort Z To A
    // =========================================================

    @Test
    public void sortProductsZToATest() {

        log.info("========== TC_PRODUCTS_008 Started ==========");

        log.info("Selecting Z to A sorting");

        Select select =
                new Select(homePage.getProductSortList());

        select.selectByValue("za");

        log.info("Z to A sorting selected");

        Select updatedSelect =
                new Select(homePage.getProductSortList());

        softAssert.assertEquals(
                updatedSelect
                        .getFirstSelectedOption()
                        .getAttribute("value"),
                "za",
                "Z to A sorting was not selected"
        );

        List<String> actualNames = new ArrayList<>();

        for (WebElement productName : homePage.getProductNames()) {

            actualNames.add(productName.getText().trim());
        }

        log.info("Actual product order: {}", actualNames);

        List<String> expectedNames =
                new ArrayList<>(actualNames);

        expectedNames.sort(Collections.reverseOrder());

        log.info("Expected Z to A order: {}", expectedNames);

        softAssert.assertEquals(
                actualNames,
                expectedNames,
                "Products are not sorted from Z to A"
        );

        log.info("Z to A sorting verified successfully");

        log.info("========== TC_PRODUCTS_008 Completed ==========");
    }
}
