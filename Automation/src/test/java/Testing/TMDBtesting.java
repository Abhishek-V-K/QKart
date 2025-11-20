package Testing;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import TMDBjavaclass.TMDBtestinghelper;

public class TMDBtesting {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://tmdb-discover.surge.sh/");
        driver.manage().window().maximize();
    }

    @Test(dataProvider = "movieFiltersData", dataProviderClass = DP.class)
    public void selectMovieFiltersTest(String type, String genre, String startYear, String endYear, double rating) {
        test = extent.createTest("Select Movie Filters Test - " + type + " | " + genre);
        TMDBtestinghelper.selectMovieFilters(driver, type, genre, startYear, endYear, rating, test);
    }


    @Test(dataProvider = "searchData", dataProviderClass = DP.class,enabled = false)
    public void movieOrTvShowSearchTest(String moviesOrShows) {
        test = extent.createTest("Search Movie/TV Show Test - " + moviesOrShows);
        TMDBtestinghelper.movieOrTvShowSearch(driver,moviesOrShows,test);
    }


    @Test(enabled = false)
    public void moviePopularAPITest() {
        test = extent.createTest("Movie Popular API Test");
        TMDBtestinghelper.moviePopularAPI(test);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            //driver.quit();
        }
    }

    @AfterClass
    public void flushReport() {
        extent.flush();
    }
}