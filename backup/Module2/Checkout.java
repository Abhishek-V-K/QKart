package QKART_SANITY_LOGIN.Module1;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import javax.xml.xpath.XPath;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/checkout";

    public Checkout(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToCheckout() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    /*
     * Return Boolean denoting the status of adding a new address
     */
    public Boolean addNewAddress(String addresString) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Click on the "Add new address" button, enter the addressString in the address text
             * box and click on the "ADD" button to save the address
             */
            WebElement addNewAddress = driver.findElement(By.xpath("//*[@id='add-new-btn']"));
            addNewAddress.click();
            WebElement address = driver.findElement(By
                    .xpath("//*[@id='root']/div/div[2]/div[1]/div/div[2]/div[1]/div/textarea[1]"));
            address.sendKeys(addresString);

            WebElement clickOnAdd = driver.findElement(
                    By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[2]/div[2]/button[1]"));
            clickOnAdd.click();
            return false;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            return false;

        }
    }

    /*
     * Return Boolean denoting the status of selecting an available address
     */
    public Boolean selectAddress(String addressToSelect) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through all the address boxes to find the address box with matching text,
             * addressToSelect and click on it
             */
            List<WebElement> selectAddress = driver.findElements(
                    By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[1]/div/div[1]/p"));
            for (WebElement selectAdd : selectAddress) {
                if (selectAdd.getText().equals(addressToSelect)) {
                    selectAdd.click();

                }
            }
            // System.out.println("Unable to find the given address");
            return false;
        } catch (Exception e) {
            System.out.println(
                    "Exception Occurred while selecting the given address: " + e.getMessage());
            return false;
        }

    }

    /*
     * Return Boolean denoting the status of place order action
     */
    public Boolean placeOrder() {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find the "PLACE ORDER" button and click on it
            Thread.sleep(5000);
            driver.findElement(By.xpath("//*[@id='root']/div/div/div/div/button[2]")).click();
            // Thread.sleep(10000);
            return true;

        } catch (Exception e) {
            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the insufficient balance message is displayed
     */
    public Boolean verifyInsufficientBalanceMessage() {
        try {
            // Create WebDriverWait instance with a 10-second timeout
            WebDriverWait wait = new WebDriverWait(driver, 10);
    
            // Wait until the alert message with ID "notistack-snackbar" is visible
            WebElement alert_msg=driver.findElement(By.id("notistack-snackbar"));

            WebElement alertMessageElement = wait.until(ExpectedConditions.visibilityOf(alert_msg));
            
            // Retrieve the text of the alert message
            String messageText = alertMessageElement.getText();
    
            // Define the expected message
            String expectedMessage = "You do not have enough balance in your wallet for this purchase";
    
            // Compare the message and return true if they match, false otherwise
            if (messageText.equals(expectedMessage)) {
                System.out.println("Alert message is correct: " + messageText);
                return true;
            } else {
                System.out.println("Incorrect message: " + messageText);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception while verifying insufficient balance message: " + e.getMessage());
            return false;
        }
    }
    
}
