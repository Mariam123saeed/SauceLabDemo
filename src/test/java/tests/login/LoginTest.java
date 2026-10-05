package tests.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import tests.BaseTest;
import tests.dataProviderTest.DataProviderTest;

import java.util.Objects;

public class LoginTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(LoginTest.class);

    // ==============================
    // TC_LOGIN_001 Valid Login
    // ==============================

    @Test(dataProvider = "credentials", dataProviderClass = DataProviderTest.class)
    public void validLoginTest(String username, String password) {

        log.info("========== TC_LOGIN_001 Valid Login Started ==========");
        log.info("Test data - Username: {}", username);
        log.info("Entering username and password");

        loginPage.login(username, password);

        log.info("Login action completed");
        log.info("Verifying user is redirected to Products page");

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://www.saucedemo.com/inventory.html",
                "User was not redirected to Products page"
        );

        log.info("Valid login successful - Products page displayed");
        log.info("========== TC_LOGIN_001 Completed ==========");
    }


    // ==============================
    // TC_LOGIN_002 Invalid Password
    // ==============================

    @Test(dataProvider = "invalidPassword", dataProviderClass = DataProviderTest.class)
    public void invalidPasswordTest(String username, String password) {

        log.info("========== TC_LOGIN_002 Invalid Password Started ==========");
        log.info("Test data - Username: {}", username);
        log.info("Entering username and invalid password");

        loginPage.login(username, password);

        log.info("Login action completed");
        log.info("Verifying error message");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service",
                "Incorrect error message"
        );

        log.info("Error message verified successfully");
        log.info("Verifying user remains on login page");

        Assert.assertTrue(
                Objects.requireNonNull(driver.getCurrentUrl()).contains("saucedemo.com"),
                "User should remain on login page"
        );

        log.info("User remained on login page successfully");
        log.info("========== TC_LOGIN_002 Completed ==========");
    }


    // ==============================
    // TC_LOGIN_003 Invalid Username
    // ==============================

    @Test(dataProvider = "invalidUserName", dataProviderClass = DataProviderTest.class)
    public void invalidUserNameTest(String username, String password) {

        log.info("========== TC_LOGIN_003 Invalid Username Started ==========");
        log.info("Test data - Username: {}", username);
        log.info("Entering invalid username and password");

        loginPage.login(username, password);

        log.info("Login action completed");
        log.info("Verifying invalid username error message");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service",
                "Incorrect error message"
        );

        log.info("Invalid username error message verified successfully");
        log.info("========== TC_LOGIN_003 Completed ==========");
    }


    // ==============================
    // TC_LOGIN_004 Invalid Credentials
    // ==============================

    @Test(dataProvider = "invalidCredentials", dataProviderClass = DataProviderTest.class)
    public void invalidLoginTest(String username, String password) {

        log.info("========== TC_LOGIN_004 Invalid Credentials Started ==========");
        log.info("Test data - Username: {}", username);
        log.info("Entering invalid username and password");

        loginPage.login(username, password);

        log.info("Login action completed");
        log.info("Verifying invalid credentials error message");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service",
                "Incorrect error message"
        );

        log.info("Invalid credentials error message verified successfully");
        log.info("========== TC_LOGIN_004 Completed ==========");
    }


    // ==============================
    // TC_LOGIN_005 Empty Username
    // ==============================

    @Test
    public void emptyUsernameTest() {

        log.info("========== TC_LOGIN_005 Empty Username Started ==========");
        log.info("Entering password only and leaving username empty");

        loginPage.enterPassword("secret_sauce");

        log.info("Clicking Login button");
        loginPage.clickLogin();

        log.info("Login action completed");
        log.info("Verifying username required error message");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username is required",
                "Incorrect error message"
        );

        log.info("Username required error message verified successfully");
        log.info("========== TC_LOGIN_005 Completed ==========");
    }


    // ==============================
    // TC_LOGIN_006 Empty Password
    // ==============================

    @Test
    public void emptyPasswordTest() {

        log.info("========== TC_LOGIN_006 Empty Password Started ==========");
        log.info("Entering username only and leaving password empty");

        loginPage.enterUsername("standard_user");

        log.info("Clicking Login button");
        loginPage.clickLogin();

        log.info("Login action completed");
        log.info("Verifying password required error message");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Password is required",
                "Incorrect error message"
        );

        log.info("Password required error message verified successfully");
        log.info("========== TC_LOGIN_006 Completed ==========");
    }


    // ==============================
    // TC_LOGIN_007 Locked User
    // ==============================

    @Test
    public void lockedUserTest() {

        log.info("========== TC_LOGIN_007 Locked User Started ==========");
        log.info("Attempting login using locked user");

        loginPage.login(
                "locked_out_user",
                "secret_sauce"
        );

        log.info("Login action completed");
        log.info("Verifying locked user error message");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Sorry, this user has been locked out.",
                "Locked user error message is incorrect"
        );

        log.info("Locked user error message verified successfully");
        log.info("========== TC_LOGIN_007 Completed ==========");
    }
}

