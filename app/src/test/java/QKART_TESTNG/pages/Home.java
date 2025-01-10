package QKART_TESTNG.pages;

import java.util.ArrayList;
import java.util.List;

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

            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("css-1urhf6j"), "Logout"));

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
            // Clear the contents of the search box and Enter the product name in the search
            // box
            WebElement searchBox = driver.findElement(By.xpath("//input[@name='search'][1]"));
            searchBox.clear();
            searchBox.sendKeys(product);

            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(String
                    .format("//div[@class='MuiCardContent-root css-1qw96cp'][1]/p[contains(text(),'%s')]", product))));
            Thread.sleep(3000);
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
            // Find all webelements corresponding to the card content section of each of
            // search results
            searchResults = driver.findElementsByClassName("css-1qw96cp");
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
            status = driver.findElementByXPath("//h4[text()=' No products found ']").isDisplayed();
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
            /*
             * Iterate through each product on the page to find the WebElement corresponding
             * to the matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            WebDriverWait wait = new WebDriverWait(driver, 20);
            
            List<WebElement> namoeOfProduct =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div/div/div[1]/p[1]")));
         for(WebElement products : namoeOfProduct){
            Thread.sleep(3000);
            
            if(products.getText().toLowerCase().contains(productName.toLowerCase())){
                WebElement addToCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div[1]/div/div[2]/button")));
                addToCart.click();
            }}
            return false;
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
            // Find and click on the the Checkout button
            WebElement checkoutBtn = driver.findElement(By.className("checkout-btn"));
            checkoutBtn.click();

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
            
            WebDriverWait wait = new WebDriverWait(driver, 10);
            
            List<WebElement> itemOnCart = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/div/div/div[1]")));
            List<WebElement> currQuantity = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/div/div[2]/div[2]/div[1]/div")));
            List<WebElement> minusButton = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/div/div[2]/div[2]/div[1]/button[1]")));   
            List<WebElement> plusButton = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div/div/div/div[2]/div[2]/div[1]/button[2]")));  
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
   
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println(("exception occurred when updating cart"));
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
           
            WebElement cartParent =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div/div[3]/div[2]/div")));
            List<WebElement> cartContents = cartParent.findElements(By.className("css-zgtx0t"));

            ArrayList<String> actualCartContents = new ArrayList<String>() {
            };
            for (WebElement cartItem : cartContents) {
                actualCartContents.add(cartItem.findElement(By.className("css-1gjj37g")).getText().split("\n")[0]);
            }

            for (String expected : expectedCartContents) {
                if (!actualCartContents.contains(expected.trim())) {
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
