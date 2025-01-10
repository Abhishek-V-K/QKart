package QKART_TESTNG;

// package QKART_TESTNG;

import QKART_TESTNG.pages.Checkout;
import QKART_TESTNG.pages.Home;
import QKART_TESTNG.pages.Login;
import QKART_TESTNG.pages.Register;
import QKART_TESTNG.pages.SearchResult;

import static org.testng.Assert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

public class QKART_Tests {

        static RemoteWebDriver driver;
        public static String lastGeneratedUserName;
        static Assertion hardAssert = new Assertion();
        static SoftAssert softAssert = new SoftAssert();

        @BeforeSuite(alwaysRun = true)
        public static void createDriver() throws MalformedURLException {
                // Launch Browser using Zalenium
                final DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName(BrowserType.CHROME);
                driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
                driver.manage().window().maximize();
                System.out.println("createDriver()");
        }

        /*
         * Testcase01: Verify a new user can successfully register
         */
        @Test(priority = 1, description = "Verify registration happens correctly",
                        groups = "Sanity_test", dataProvider = "data-provider",
                        dataProviderClass = DP.class)
        public void TestCase01(@Optional("defaultUser") String username,
                        @Optional("defaultPassword") String password) throws InterruptedException {
                Boolean status;

                // Visit the Registration page and register a new user
                Register registration = new Register(driver);
                registration.navigateToRegisterPage();
                status = registration.registerUser(username, password, true);
                assertTrue(status, "Failed to register new user");

                // Save the last generated username
                lastGeneratedUserName = registration.lastGeneratedUsername;

                // Visit the login page and login with the previuosly registered user
                Login login = new Login(driver);
                login.navigateToLoginPage();
                status = login.PerformLogin(lastGeneratedUserName, password);

                assertTrue(status, "Failed to login with registered user");

                // Visit the home page and log out the logged in user
                Home home = new Home(driver);
                status = home.PerformLogout();

        }

        @Test(priority = 2, description = "Verify re-registering an already registered user fails",
                        groups = "Sanity_test", dataProvider = "data-provider",
                        dataProviderClass = DP.class)
        public void TestCase02(@Optional("defaultUser") String username,
                        @Optional("defaultPassword") String password) throws InterruptedException {
                Boolean status;
                // Visit the Registration page and register a new user
                Register registration = new Register(driver);
                registration.navigateToRegisterPage();
                status = registration.registerUser(username, password, true);

                softAssert.assertTrue(status, "User registration failed");

                // Save the last generated username
                lastGeneratedUserName = registration.lastGeneratedUsername;

                // Visit the Registration page and try to register using the previously
                // registered user's credentials
                registration.navigateToRegisterPage();
                status = registration.registerUser(lastGeneratedUserName, "abc@123", false);

                // If status is true, then registration succeeded, else registration has
                // failed. In this case registration failure means Success
                softAssert.assertAll();

        }

        /*
         * Verify the functinality of the search text box
         */
        @Test(priority = 3, description = "Verify the functionality of search text box",
                        groups = "Sanity_test", dataProvider = "data-provider",
                        dataProviderClass = DP.class)
        public void TestCase03(String productName) throws InterruptedException {
                boolean status;


                // Visit the home page
                Home homePage = new Home(driver);
                homePage.navigateToHome();

                // Search for the "yonex" product
                status = homePage.searchForProduct(productName);

                assertTrue(status,
                                "TestCase 3 : Test Case Failure. Unable to search for given product");

                // Fetch the search results
                List<WebElement> searchResults = homePage.getSearchResults();

                // Verify the search results are available
                assertFalse(searchResults.size() == 0,
                                "TestCase 3 : Test Case Failure. There were no results for the given search string");

                for (WebElement webElement : searchResults) {
                        // Create a SearchResult object from the parent element
                        SearchResult resultelement = new SearchResult(webElement);

                        // Verify that all results contain the searched text
                        String elementText = resultelement.getTitleofResult();

                        assertTrue(elementText.toUpperCase().contains(productName.toUpperCase()),
                                        "TestCase 3 : Test Case Failure. Test Results contains un-expected values: "
                                                        + elementText);
                }


                // Search for product
                status = homePage.searchForProduct("Gesundheit");
                assertFalse(status,
                                "TestCase 3 : Test Case Failure. Invalid keyword returned results");

                // Verify no search results are found
                searchResults = homePage.getSearchResults();
                if (searchResults.size() == 0) {
                        assertTrue(homePage.isNoResultFound(),
                                        "Step Success : Successfully validated that no products found message is displayed");
                } else {
                        hardAssert.fail("TestCase 3 : Test Case Fail. Expected: no results , actual: Results were available");

                }


        }

        /*
         * Verify the presence of size chart and check if the size chart content is as expected
         */
        @Test(priority = 4,
                        description = "Verify the existence of size chart for certain items and validate contents of size chart",
                        groups = "Regression_Test", dataProvider = "data-provider",
                        dataProviderClass = DP.class)
        public void TestCase04(String productName) throws InterruptedException {
                boolean status = false;

                // Visit home page
                Home homePage = new Home(driver);
                homePage.navigateToHome();

                // Search for product and get card content element of search results
                status = homePage.searchForProduct(productName);
                List<WebElement> searchResults = homePage.getSearchResults();

                // Create expected values
                List<String> expectedTableHeaders =
                                Arrays.asList("Size", "UK/INDIA", "EU", "HEEL TO TOE");
                List<List<String>> expectedTableBody =
                                Arrays.asList(Arrays.asList("6", "6", "40", "9.8"),
                                                Arrays.asList("7", "7", "41", "10.2"),
                                                Arrays.asList("8", "8", "42", "10.6"),
                                                Arrays.asList("9", "9", "43", "11"),
                                                Arrays.asList("10", "10", "44", "11.5"),
                                                Arrays.asList("11", "11", "45", "12.2"),
                                                Arrays.asList("12", "12", "46", "12.6"));

                // Verify size chart presence and content matching for each search result
                for (WebElement webElement : searchResults) {
                        SearchResult result = new SearchResult(webElement);

                        // Verify if the size chart exists for the search result
                        if (result.verifySizeChartExists()) {
                                // Verify if size dropdown exists
                                status = result.verifyExistenceofSizeDropdown(driver);
                                assertTrue(status,
                                                "Step Success : Validated presence of drop down : PASS");
                                // Open the size chart
                                if (result.openSizechart()) {
                                        // Verify if the size chart contents matches the expected
                                        // values
                                        assertTrue(result.validateSizeChartContents(
                                                        expectedTableHeaders, expectedTableBody,
                                                        driver),
                                                        "Step Failure Failure while validating contents of Size Chart Link");

                                        // Close the size chart modal
                                        status = result.closeSizeChart(driver);

                                }

                        } else {
                                hardAssert.fail("TestCase 4 : Test Case Fail. Size Chart Link does not exist");

                        }
                }
                assertTrue(status, "TestCase 4 End Test Case: Validated Size Chart Details | FAIL");

        }

        /*
         * Verify the complete flow of checking out and placing order for products is working
         * correctly
         */
        @Test(priority = 5,
                        description = "Verify that a new user can add multiple products in to the cart and Checkout",
                        groups = "Sanity_test", dataProvider = "data-provider",
                        dataProviderClass = DP.class)

        public void TestCase05(String product_1, String product_2, String address)
                        throws InterruptedException {
                Boolean status;

                // Go to the Register page
                Register registration = new Register(driver);
                registration.navigateToRegisterPage();

                // Register a new user
                status = registration.registerUser("testUser", "abc@123", true);

                assertTrue(status, "TestCase 5 Test Case Failure. Happy Flow Test Failed");

                // Save the username of the newly registered user
                lastGeneratedUserName = registration.lastGeneratedUsername;

                // Go to the login page
                Login login = new Login(driver);
                login.navigateToLoginPage();

                // Login with the newly registered user's credentials
                status = login.PerformLogin(lastGeneratedUserName, "abc@123");
                assertTrue(status, "TestCase 5 Step Failure User Perform Login Failed");

                // Go to the home page
                Home homePage = new Home(driver);
                homePage.navigateToHome();

                // Find required products by searching and add them to the user's cart
                status = homePage.searchForProduct(product_1);
                homePage.addProductToCart(product_1);
                status = homePage.searchForProduct(product_2);
                homePage.addProductToCart(product_2);

                // Click on the checkout button
                homePage.clickCheckout();

                // Add a new address on the Checkout page and select it
                Checkout checkoutPage = new Checkout(driver);
                checkoutPage.addNewAddress(address);
                checkoutPage.selectAddress(address);

                // Place the order
                checkoutPage.placeOrder();

                WebDriverWait wait = new WebDriverWait(driver, 30);
                wait.until(ExpectedConditions
                                .urlToBe("https://crio-qkart-frontend-qa.vercel.app/thanks"));

                // Check if placing order redirected to the Thansk page
                status = driver.getCurrentUrl().endsWith("/thanks");

                // Go to the home page
                homePage.navigateToHome();

                // Log out the user
                homePage.PerformLogout();

        }


        /*
         * Verify the quantity of items in cart can be updated
         */
        @Test(priority = 6, description = "Verify that the contents of the cart can be edited",
                        groups = "Regression_Test", dataProvider = "data-provider",
                        dataProviderClass = DP.class)
        public void TestCase06(String product_1, String product_2) throws InterruptedException {
                Boolean status;
                Home homePage = new Home(driver);
                Register registration = new Register(driver);
                Login login = new Login(driver);

                registration.navigateToRegisterPage();
                status = registration.registerUser("testUser", "abc@123", true);

                softAssert.assertTrue(status,
                                "Test Case 6 Step Failure User Perform Register Failed");
                lastGeneratedUserName = registration.lastGeneratedUsername;

                login.navigateToLoginPage();
                status = login.PerformLogin(lastGeneratedUserName, "abc@123");

                softAssert.assertTrue(status, "Test Case 6 Step Failure User Perform Login Failed");

                homePage.navigateToHome();
                status = homePage.searchForProduct("Xtend");
                homePage.addProductToCart(product_1);

                status = homePage.searchForProduct("Yarine");
                homePage.addProductToCart(product_2);

                // update watch quantity to 2
                homePage.changeProductQuantityinCart(product_1, 2);

                // update table lamp quantity to 0
                homePage.changeProductQuantityinCart(product_2, 0);

                // update watch quantity again to 1
                homePage.changeProductQuantityinCart(product_1, 1);

                homePage.clickCheckout();

                Checkout checkoutPage = new Checkout(driver);
                checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 3");
                checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 3");

                checkoutPage.placeOrder();

                try {
                        WebDriverWait wait = new WebDriverWait(driver, 30);
                        wait.until(ExpectedConditions.urlToBe(
                                        "https://crio-qkart-frontend-qa.vercel.app/thanks"));
                } catch (TimeoutException e) {
                        System.out.println("Error while placing order in: " + e.getMessage());

                }

                status = driver.getCurrentUrl().endsWith("/thanks");

                homePage.navigateToHome();
                homePage.PerformLogout();
                softAssert.assertAll();

        }

        @Test(priority = 7,
                        description = "Verify that insufficient balance error is thrown when the wallet balance is not enough",
                        groups = "Sanity_test",dependsOnGroups = "Regression_Test", dataProvider = "data-provider",
                        dataProviderClass = DP.class)
        public void TestCase07(String productName, int quantity) throws InterruptedException {
                Boolean status;

                Register registration = new Register(driver);
                registration.navigateToRegisterPage();
                status = registration.registerUser("testUser", "abc@123", true);

                softAssert.assertTrue(status,
                                "Test Case 7 Step Failure User Perform Registration Failed");
                lastGeneratedUserName = registration.lastGeneratedUsername;

                Login login = new Login(driver);
                login.navigateToLoginPage();
                status = login.PerformLogin(lastGeneratedUserName, "abc@123");

                softAssert.assertTrue(status, "Test Case 7 Step Failure User Perform Login Failed");

                Home homePage = new Home(driver);
                homePage.navigateToHome();
                status = homePage.searchForProduct(productName);
                homePage.addProductToCart(productName);

                homePage.changeProductQuantityinCart(productName, quantity);

                homePage.clickCheckout();

                Checkout checkoutPage = new Checkout(driver);
                checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 3");
                checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 3");

                checkoutPage.placeOrder();
                Thread.sleep(3000);

                status = checkoutPage.verifyInsufficientBalanceMessage();

                softAssert.assertTrue(status,
                                "Test Case 7: Verify that insufficient balance error is thrown when the wallet balance is not enough: Passed");
                softAssert.assertAll();

        }

        @Test(priority = 8,
                        description = "Verify that a product added to a cart is available when a new tab is added",
                        groups = "Regression_Test", dataProvider = "data-provider",
                        dataProviderClass = DP.class)
        public void TestCase08(String productName) throws InterruptedException {
                Thread.sleep(3000);
                Boolean status = false;
                Thread.sleep(3000);

                Register registration = new Register(driver);
                registration.navigateToRegisterPage();
                status = registration.registerUser("testUser", "abc@123", true);

                softAssert.assertTrue(status,
                                "TestCase 8 Test Case Failure. Verify that product added to cart is available when a new tab is opened");
                lastGeneratedUserName = registration.lastGeneratedUsername;

                Login login = new Login(driver);
                login.navigateToLoginPage();
                status = login.PerformLogin(lastGeneratedUserName, "abc@123");

                softAssert.assertTrue(status, "Test Case 8 Step Failure User Perform Login Failed");

                Home homePage = new Home(driver);
                homePage.navigateToHome();

                status = homePage.searchForProduct(productName);
                homePage.addProductToCart(productName);

                // Open a new tab
                driver.switchTo().newWindow(WindowType.TAB);
                homePage.navigateToHome();

                // Get all window handles and store them in a list
                List<String> tabs = new ArrayList<>(driver.getWindowHandles());

                // Switch to the new tab (index 1)
                driver.switchTo().window(tabs.get(1));

                // List of expected cart contents
                List<String> expected = Arrays.asList(productName);

                Thread.sleep(3000);
                // Validate cart contents
                if (homePage.verifyCartContents(expected)) {
                        logStatus("Step Success", "Successfully validated contents of the cart",
                                        "PASS");

                        // Close the current tab (new tab)
                        driver.close();

                        // Switch back to the original tab (index 0)
                        driver.switchTo().window(tabs.get(0));
                } else {
                        logStatus("Step Failure", "Failure while validating contents of the cart",
                                        "FAIL");
                        // Take Screenshot "Failed", "Test Case 8"
                        takeScreenshot(driver, "Failed", "Test Case 8");
                }

                // Log the end of the test case
                logStatus("End TestCase", "Test Case PASS. Validated contents in the new tab",
                                "PASS");

                logStatus("End TestCase", "Test Case PASS. Validated contents in the new tab",
                                "PASS");



                softAssert.assertAll();

        }

        @Test(priority = 9,
                        description = "Verify that privacy policy and about us links are working fine",
                        groups = "Regression_Test")
        public void TestCase09() throws InterruptedException {
                Boolean status = false;

                Register registration = new Register(driver);
                registration.navigateToRegisterPage();
                status = registration.registerUser("testUser", "abc@123", true);

                softAssert.assertTrue(status,
                                "TestCase 09 Test Case Failure Verify that the Privacy Policy, About Us are displayed correctly");
                lastGeneratedUserName = registration.lastGeneratedUsername;

                Login login = new Login(driver);
                login.navigateToLoginPage();
                status = login.PerformLogin(lastGeneratedUserName, "abc@123");

                softAssert.assertTrue(status, "TestCase 09 Step Failure User Perform Login Failed");

                Home homePage = new Home(driver);
                homePage.navigateToHome();

                WebDriverWait wait = new WebDriverWait(driver, 10);
                List<WebElement> element = driver.findElements(
                                By.xpath("//*[@id='root']/div/div/div[5]/div[2]/p/a"));
                for (WebElement verifyLinks : element) {
                        ((JavascriptExecutor) driver).executeScript(
                                        "arguments[0].scrollIntoView(true);", verifyLinks);
                        verifyLinks.click();
                        String currUrl = driver.getCurrentUrl();
                        if (currUrl.equals("https://crio-qkart-frontend-qa.vercel.app/")) {

                                logStatus("TestCase 9",
                                                "Verify that the url of the current tab does not change",
                                                "PASS");

                                // Get all window handles
                                Set<String> windowHandles = driver.getWindowHandles();
                                ArrayList<String> tabs = new ArrayList<>(windowHandles);

                                // Switch to the next tab
                                driver.switchTo().window(tabs.get(1));
                                boolean isPresent = driver
                                                .findElement(By.xpath(
                                                                "//*[@id='root']/div/div[2]/p"))
                                                .isDisplayed();
                                if (isPresent) {
                                        logStatus("TestCase 9", "Content is displayed", "PASS");
                                } else {
                                        logStatus("TestCase 9", "Content is not displayed", "FAIL");
                                }
                                driver.close();


                                driver.switchTo().window(tabs.get(0));
                        }
                }
                softAssert.assertAll();

        }

        @Test(priority = 10, description = "Verify that the contact us dialog works fine",
                        groups = "Regression_Test", dataProvider = "data-provider",
                        dataProviderClass = DP.class)
        public void TestCase10(String Name, String Email, String Message)
                        throws InterruptedException {

                takeScreenshot(driver, "StartTestCase", "TestCase10");

                Home homePage = new Home(driver);
                homePage.navigateToHome();

                driver.findElement(By.xpath("//*[text()='Contact us']")).click();

                WebElement name = driver.findElement(By.xpath("//input[@placeholder='Name']"));
                name.sendKeys(Name);
                WebElement email = driver.findElement(By.xpath("//input[@placeholder='Email']"));
                email.sendKeys(Email);
                WebElement message =
                                driver.findElement(By.xpath("//input[@placeholder='Message']"));
                message.sendKeys(Message);

                WebElement contactUs = driver.findElement(By.xpath(
                                "/html/body/div[2]/div[3]/div/section/div/div/div/form/div/div/div[4]/div/button"));
                softAssert.assertTrue(contactUs.isEnabled(),
                                "Test case 10 Verify that contact us option is not working correctly Passed");
                contactUs.click();

                WebDriverWait wait = new WebDriverWait(driver, 30);
                wait.until(ExpectedConditions.invisibilityOf(contactUs));

                softAssert.assertAll();
        }

        @Test(priority = 11,
                        description = "Ensure that the Advertisement Links on the QKART page are clickable",
                        groups = "Sanity_test")
        public void TestCase11() throws InterruptedException {
                Boolean status = false;

                Register registration = new Register(driver);
                registration.navigateToRegisterPage();
                status = registration.registerUser("testUser", "abc@123", true);

                softAssert.assertTrue(status,
                                "Test Case 11 Test Case Failure. Ensure that the links on the QKART advertisement are clickable");
                lastGeneratedUserName = registration.lastGeneratedUsername;

                Login login = new Login(driver);
                login.navigateToLoginPage();
                status = login.PerformLogin(lastGeneratedUserName, "abc@123");

                softAssert.assertTrue(status,
                                "Test Case 11 Step Failure User Perform Login PASSED");

                Home homePage = new Home(driver);
                homePage.navigateToHome();

                status = homePage.searchForProduct("YONEX Smash Badminton Racquet");
                homePage.addProductToCart("YONEX Smash Badminton Racquet");
                homePage.changeProductQuantityinCart("YONEX Smash Badminton Racquet", 1);
                homePage.clickCheckout();

                Checkout checkoutPage = new Checkout(driver);
                checkoutPage.addNewAddress("Addr line 1  addr Line 2  addr line 3");
                checkoutPage.selectAddress("Addr line 1  addr Line 2  addr line 3");
                checkoutPage.placeOrder();
                Thread.sleep(3000);
                WebDriverWait wait = new WebDriverWait(driver, 15);
                List<WebElement> iframeElements = wait.until(ExpectedConditions
                                .presenceOfAllElementsLocatedBy(By.tagName("iframe")));
                int totalElements = iframeElements.size();

                assertTrue(totalElements == 3, "TestCase 11 - 3 iframe elements are not present");

                // Explicit wait for the iframe container to load

                wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//*[@id='root']/div/div[2]/div/iframe")));

                // Loop through iframes dynamically
                for (int i = 0;; i++) {
                        // Refresh iframe list to avoid stale references
                        List<WebElement> iframes = wait.until(
                                        ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(
                                                        "//*[@id='root']/div/div[2]/div/iframe")));
                        int all = iframes.size();

                        if (i >= all)
                                break; // Exit loop if no more iframes

                        // Switch to default content, then switch to iframe
                        driver.switchTo().defaultContent();
                        wait.until(ExpectedConditions
                                        .frameToBeAvailableAndSwitchToIt(iframes.get(i)));

                        try {
                                // Wait for the buy button to appear
                                WebElement buyButton = wait.until(
                                                ExpectedConditions.elementToBeClickable(By.xpath(
                                                                "//*[@id='continue-btn'][2]")));
                                buyButton.click();

                                driver.navigate().back();
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }


                driver.switchTo().parentFrame();
        }

        @AfterSuite
        public static void quitDriver() {
                System.out.println("quit()");
                driver.quit();
        }

        public static void logStatus(String type, String message, String status) {

                System.out.println(String.format("%s |  %s  |  %s | %s",
                                String.valueOf(java.time.LocalDateTime.now()), type, message,
                                status));
        }

        public static void takeScreenshot(WebDriver driver, String screenshotType,
                        String description) {
                try {
                        File theDir = new File("/screenshots");
                        if (!theDir.exists()) {
                                theDir.mkdirs();
                        }
                        String timestamp = String.valueOf(java.time.LocalDateTime.now());
                        String fileName = String.format("screenshot_%s_%s_%s.png", timestamp,
                                        screenshotType, description);
                        TakesScreenshot scrShot = ((TakesScreenshot) driver);
                        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
                        File DestFile = new File("screenshots/" + fileName);
                        FileUtils.copyFile(SrcFile, DestFile);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
