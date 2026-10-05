package tests.dataProviderTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.login.LoginTest;
import utils.CSVFileManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataProviderTest {

    @DataProvider(name = "credentials")
    public Object[][] getLoginData() {
        return new Object[][]{
                {"standard_user", "secret_sauce"}
        };
    }

    @DataProvider(name = "invalidUserName")
    public Object[][] getInvalidUserName() {
        return new Object[][]{
                {"Mariam", "secret_sauce"},
                {"Amr", "secret_sauce"},
                {"Ahmed", "secret_sauce"},
                {"zain", "secret_sauce"},
                {"Sara", "secret_sauce"}
        };
    }

    @DataProvider(name = "invalidPassword")
    public Object[][] getInvalidPassword() {
        return new Object[][]{
                {"standard_user", "_sauce"},
                {"standard_user", "s"},
                {"standard_user", "wrong123"},
                {"standard_user", "secret"},
                {"standard_user", "Secret_sauce"}
        };
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] getInvalidCredentials() {
        return new Object[][]{
                {"Mariam", "123"},
                {"ahmed", "Mmm2525"}
        };
    }


    @DataProvider(name = "expectedProducts")
    public Object[][] expectedProducts() {

        List<String> expectedProductNames = Arrays.asList(
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light",
                "Sauce Labs Bolt T-Shirt",
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Onesie",
                "Test.allTheThings() T-Shirt (Red)"
        );

        return new Object[][]{{expectedProductNames}
        };
    }

    @DataProvider(name = "productNames")
    public Object[][] getProductNamesData() throws Exception {

        CSVFileManager csvFileManager = new CSVFileManager("src/main/resources/products.csv");

        Map<String, List<String>> columns = csvFileManager.getColumnsWithData();

        List<String> indexes = columns.get("Index");
        List<String> productNames = columns.get("ProductName");
        //Map<String, List<String>> mostFrequentWords = csvFileManager.getMostFrequentWordsPerColumn();

        Object[][] data = new Object[productNames.size()][2];

        for (int i = 0; i < productNames.size(); i++) {

            data[i][0] = Integer.parseInt(indexes.get(i));
            data[i][1] = productNames.get(i);
        }

        return data;
    }



    }
