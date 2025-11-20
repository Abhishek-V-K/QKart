package TMDBjavaclass;

import io.restassured.RestAssured;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;




public class TMDBtestinghelper {

    private static final Logger logger = LoggerFactory.getLogger(TMDBtestinghelper.class);


    public static void selectMovieFilters(WebDriver driver, String type, String genre, String startYear, String endYear, double rating, ExtentTest test) {
        try {
            test.info("Selecting movie filters: Type=" + type + ", Genre=" + genre + ", StartYear=" + startYear + ", EndYear=" + endYear + ", Rating=" + rating);
            logger.info("Selecting movie filters: Type={}, Genre={}, StartYear={}, EndYear={}, Rating={}", type, genre, startYear, endYear, rating);

            // Type
            WebElement typeInput = driver.findElement(By.cssSelector("input[id='react-select-2-input']"));
            typeInput.sendKeys(type);
            typeInput.sendKeys(Keys.ENTER);
            logger.debug("Entered MOVIE/TV Shows TYPE: {}", type);
//            test.pass("Entered Type: " + type)
//                    .addScreenCaptureFromPath(captureScreenshot(driver, "type_pass"));

            // Genre
            WebElement genreInput = driver.findElement(By.cssSelector("input[id='react-select-3-input']"));
            genreInput.sendKeys(genre);
            genreInput.sendKeys(Keys.ENTER);
            logger.debug("Entered Genre: {}", genre);
//            test.pass("Entered Genre: " + genre)
//                    .addScreenCaptureFromPath(captureScreenshot(driver, "genre_pass"));

            // Start Year
            WebElement startYearInput = driver.findElement(By.cssSelector("input[id='react-select-4-input']"));
            startYearInput.sendKeys(startYear);
            startYearInput.sendKeys(Keys.ENTER);
            logger.debug("Entered Start Year: {}", startYear);
//            test.pass("Entered Start Year: " + startYear)
//                    .addScreenCaptureFromPath(captureScreenshot(driver, "startYear_pass"));

            // End Year
            WebElement endYearInput = driver.findElement(By.cssSelector("input[id='react-select-5-input']"));
            endYearInput.sendKeys(endYear);
            endYearInput.sendKeys(Keys.ENTER);
            logger.debug("Entered End Year: {}", endYear);
//            test.pass("Entered End Year: " + endYear)
//                    .addScreenCaptureFromPath(captureScreenshot(driver, "endYear_pass"));

            // Rating
            int posinset = (int) Math.ceil(rating);
            int divIndex = (int) ((rating * 2) % 2 == 0 ? 2 : 1);
            String ratingXpath = String.format("//div[@aria-posinset='%d']/div[%d]", posinset, divIndex);

            WebElement ratingElement = driver.findElement(By.xpath(ratingXpath));
            ratingElement.click();
            logger.info("Selected rating: {}", rating);
//            test.pass("Selected Rating: " + rating)
//                    .addScreenCaptureFromPath(captureScreenshot(driver, "rating_pass"));

        } catch (Exception e) {
            logger.error("Error while selecting movie filters", e);
//            test.fail("Failed to select movie filters: " + e.getMessage())
//                    .addScreenCaptureFromPath(captureScreenshot(driver, "filters_fail"));
        }
    }


   
        
public static void movieOrTvShowSearch(WebDriver driver, String movieOrShow, ExtentTest test) {
    try {
        logger.info("Searching for movie/TV show: {}", movieOrShow);
        test.info("Searching for movie/TV show: " + movieOrShow);

        WebElement element = driver.findElement(By.xpath("//input[@name='search']"));
        element.sendKeys(movieOrShow);
        test.pass("Entered search term: " + movieOrShow);

        element.sendKeys(Keys.ENTER);
        test.pass("Pressed ENTER to search");
    } catch (Exception e) {
        logger.error("Error during search", e);
        test.fail("Error during search: " + e.getMessage());

    }
}

    public static void moviePopularAPI(ExtentTest test) {
        try {
            logger.info("Calling Movie Popular API...");
            RestAssured.baseURI = "https://api.themoviedb.org/3";
            RestAssured.useRelaxedHTTPSValidation();

            Response response = given()
                    .queryParam("api_key", "add494e96808c55b3ee7f940c9d5e5b6")
                    .queryParam("page", "1")
                    .when()
                    .get("/movie/popular");

            logger.info("Status Code: {}", response.getStatusCode());
            logger.debug("Response Body: {}", response.asString());

            if (!response.getContentType().contains("json")) {
                logger.error("Expected JSON but got: {}", response.getContentType());
                throw new RuntimeException("Invalid content type");
            }

            JsonPath jsonPath = response.jsonPath();
            List<String> titles = jsonPath.getList("results.title");
            logger.info("Popular Movie Titles: {}", titles);
        } catch (Exception e) {
            logger.error("Error calling Movie Popular API", e);
        }
    }

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        String filePath = "C:\\Users\\AVKAMALA\\Downloads\\Automation\\";
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            org.openqa.selenium.io.FileHandler.copy(src, dest);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
        }
        return filePath;
    }

//    public static void main(String[] args) {
//        ExtentSparkReporter spark = new ExtentSparkReporter("extent-report.html");
//        ExtentReports extent = new ExtentReports();
//        extent.attachReporter(spark);
//        ExtentTest test = extent.createTest("TMDB Automation Test");
//
//        logger.info("Automation started.");
//        WebDriver driver = null;
//        try {
//            driver = new ChromeDriver();
//            driver.get("https://tmdb-discover.surge.sh/");
//            driver.manage().window().maximize();
//            logger.info("Navigated to TMDB Discover page.");
//
//            // Uncomment as needed
//            selectMovieFilters(driver, "Movie", "Action", "1900", "2024", 3.5,test);
//            movieOrTvShowSearch(driver, "Fantastic Four");
//            //moviePopularAPI();
//        } catch (Exception e) {
//            logger.error("Error in main execution", e);
//        } finally {
//            if (driver != null) {
//                driver.quit();
//                logger.info("Browser closed.");
//            }
//        }
//        extent.flush();
//    }
}