package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // SLEEP_STMT_10: Wait for Logout to complete
            // Wait for Logout to Complete
            Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any
     * errors
     */
    public Boolean searchForProduct(String product) {
        try {
            
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            WebElement searchBox = driver.findElement(By.xpath("//*[@id='root']/div/div/div[1]/div[2]/div/input"));
            // Clear the contents of the search box and Enter the product name in the search
            // box
            
            searchBox.sendKeys(product);
            searchBox.clear();
             Thread.sleep(2000);
            // WebDriverWait wait = new WebDriverWait(driver, 10);
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div[1]/div")));

            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {
        };
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            
            searchResults = driver.findElements(By.xpath("//*[@id='root']/div/div/div[3]/div/div[2]/div"));
            // Find all webelements corresponding to the card content section of each of
            // search results
           
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            WebElement noProductFound = driver.findElement(By.xpath("//*[@id='root']/div/div/div[3]/div/div[2]/div/h4"));
           status =  noProductFound.getText().equals("No products found");

            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through each product on the page to find the WebElement corresponding
             * to the matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
        
         List<WebElement> namoeOfProduct =  driver.findElements(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div/div/div[1]/p[1]"));
         for(WebElement products : namoeOfProduct){
            Thread.sleep(3000);
            
            if(products.getText().equalsIgnoreCase(productName)){
                Thread.sleep(4000);
                WebElement addToCart = driver.findElement(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div[1]/div/div[2]/button"));
                addToCart.click();
            }
         }
            
            
            return true;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find and click on the the Checkout button
            WebElement checkout = driver.findElement(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/button"));
            checkout.click();
            status = true;
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart
     * operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {
            {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5

            // Find the item on the cart with the matching productName
             
             List<WebElement> itemOnCart = driver.findElements(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/div/div/div[1]"));
             List<WebElement> currQuantity = driver.findElements(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/div/div[2]/div[2]/div[1]/div"));
             List<WebElement> minusButton = driver.findElements(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/div/div[2]/div[2]/div[1]/button[1]"));   
             List<WebElement> plusButton = driver.findElements(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/div/div[2]/div[2]/div[1]/button[2]"));  
             for (int i = 0; i < itemOnCart.size(); i++) {
                // Check if the current item's name matches the given product name
                if (itemOnCart.get(i).getText().equalsIgnoreCase(productName)) {
                    // Parse the current quantity as an integer
                    int currentQuantity = Integer.parseInt(currQuantity.get(i).getText());
    
                    // Adjust quantity as needed
                    while (currentQuantity < quantity) {
                        plusButton.get(i).click();
                        Thread.sleep(1000);
                        currentQuantity++;
                    }
    
                    while (currentQuantity > quantity) {
                        minusButton.get(i).click();
                        Thread.sleep(1000);
                        currentQuantity--;
                    }
    
                    // If quantity is set to 0, the item is expected to be removed from the cart
                    if (quantity == 0) {
                        // Wait for the item to disappear (optional, based on UI behavior)
                        Thread.sleep(1000); // Adjust based on your app's responsiveness
                    }
    
                    return true; // Quantity successfully updated
                }
            }
             }
            // Increment or decrement the quantity of the matching product until the current
            // quantity is reached (Note: Keep a look out when then input quantity is 0,
            // here we need to remove the item completely from the cart)


            return false;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            WebElement cartParent = driver.findElement(By.className("cart"));
            List<WebElement> cartContents = cartParent.findElements(By.className("css-zgtx0t"));
            Thread.sleep(3000);
            ArrayList<String> actualCartContents = new ArrayList<String>() {
            };
            for (WebElement cartItem : cartContents) {
                actualCartContents.add(cartItem.findElement(By.className("css-1gjj37g")).getText().split("\n")[0]);
            }

            for (String expected : expectedCartContents) {
                Thread.sleep(3000);
                if (!actualCartContents.contains(expected)) {
                    Thread.sleep(3000);
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
}
