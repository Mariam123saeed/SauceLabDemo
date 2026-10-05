package tests;

import driverFactory.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.FileUtils;
import org.example.pages.login.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigHandler;
import utils.ScreenShot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    public static WebDriver driver;
    public LoginPage loginPage;
    public String browserName = "chrome";
    public ConfigHandler configHandler;

    // ==============================
    // Set up
    // ==============================

    @BeforeMethod
    public void setUp() throws IllegalAccessException {

        log.info("========== Test Setup Started ==========");
        log.info("Loading configuration file");

        configHandler = new ConfigHandler("src/main/resources/config.properties");
        browserName = configHandler.getValue("browser");

        log.info("Browser selected: {}", browserName);
        String url = configHandler.getValue("url");

        log.info("Application URL: {}", url);
        log.info("Creating WebDriver");
        driver = DriverFactory.getWebDriver(browserName);
        log.info("WebDriver created successfully");

        log.info("Opening application");
        driver.get(url);
        log.info("Application opened successfully");
        log.info("Maximizing browser window");
        driver.manage().window().maximize();

        log.info("Initializing LoginPage");
        loginPage = new LoginPage(driver);

        log.info("LoginPage initialized successfully");
        log.info("========== Test Setup Completed ==========");
    }

    // ==============================
    // Tear Down
    // ==============================

    @AfterMethod
    public void failedTestCase(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE){
          File image =ScreenShot.takeScreenShot(driver);
            FileInputStream fis = new FileInputStream(image);
            Allure.addAttachment("Failure Screenshot for TC: "+result.getTestName(),"image/png",fis,"png");
        }

    }

    @AfterMethod
    public void tearDown() throws IllegalAccessException {

        log.info("========== Test Tear Down Started ==========");

        if (driver != null) {
            log.info("Closing WebDriver");

            DriverFactory.quitWebDriver(browserName);
            driver = null;

            log.info("WebDriver closed successfully");
        } else {
            log.warn("WebDriver is already null. Nothing to close.");
        }

        log.info("========== Test Tear Down Completed ==========");
    }
}