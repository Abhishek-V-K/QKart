import io.restassured.RestAssured;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import java.util.List;


public class Test {

    public static void selectMovieFilters(WebDriver driver,String type, String genre, String startYear, String endYear, double rating) {
        // Select Type
        WebElement typeInput = driver.findElement(By.cssSelector("input[id='react-select-2-input']"));
        typeInput.sendKeys(type);
        typeInput.sendKeys(Keys.ENTER);
        System.out.println("Entered MOVIE/TV Shows TYPE");

        // Select Genre
        WebElement genreInput = driver.findElement(By.cssSelector("input[id='react-select-3-input']"));
        genreInput.sendKeys(genre);
        genreInput.sendKeys(Keys.ENTER);

        // Select Starting Year
        WebElement startYearInput = driver.findElement(By.cssSelector("input[id='react-select-4-input']"));
        startYearInput.sendKeys(startYear);
        startYearInput.sendKeys(Keys.ENTER);

        // Select Ending Year
        WebElement endYearInput = driver.findElement(By.cssSelector("input[id='react-select-5-input']"));
        endYearInput.sendKeys(endYear);
        endYearInput.sendKeys(Keys.ENTER);

        // Handle Ratings
        // Each aria-posinset represents a block of ratings, and div inside represents increments of 0.5
        int posinset = (int) Math.ceil(rating); // e.g., rating 2.5 -> posinset 3
        int divIndex = (int) ((rating * 2) % 2 == 0 ? 2 : 1); // 0.5 -> div[1], 1 -> div[2]

        String ratingXpath = String.format("//div[@aria-posinset='%d']/div[%d]", posinset, divIndex);
        WebElement ratingElement = driver.findElement(By.xpath(ratingXpath));
        ratingElement.click();
    }

    public static void movieOrTvShowSearch(WebDriver driver,String movieOrShow){
        WebElement element = driver.findElement(By.xpath("//input[@name=\"search\"]"));
        element.sendKeys(movieOrShow);
        element.sendKeys(Keys.ENTER);
    }



    public static void moviePopularAPI() {
        RestAssured.baseURI = "https://api.themoviedb.org/3";
        RestAssured.useRelaxedHTTPSValidation(); // Fix SSL issues

        Response response = given()
                .queryParam("api_key", "add494e96808c55b3ee7f940c9d5e5b6")
                .queryParam("page", "1")
                .when()
                .get("/movie/popular");

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Content-Type: " + response.getContentType());
        System.out.println("Response Body: " + response.asString());

        if (!response.getContentType().contains("json")) {
            throw new RuntimeException("Expected JSON but got: " + response.getContentType());
        }

        JsonPath jsonPath = response.jsonPath();
        List<String> titles = jsonPath.getList("results.title");

        System.out.println("Popular Movie Titles:");
        titles.forEach(System.out::println);
    }

    public static void moviePopularAPI1() {
        // Full URL
        String url = "https://api.themoviedb.org/3/movie/popular?page=1&api_key=add494e96808c55b3ee7f940c9d5e5b6";

        // Send GET request using full URL
        Response response = get(url);

        // Print status and response body
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Content-Type: " + response.getContentType());
        System.out.println("Response Body: " + response.asString());
    }

    public static void dummyApiExample() {
        baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Print raw response for debugging
        System.out.println("Response Body: " + response.asString());

        // Parse JSON
        JsonPath jsonPath = response.jsonPath();
        List<String> titles = jsonPath.getList("title");

        System.out.println("Post Titles:");
        titles.forEach(System.out::println);
    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");
        WebDriver driver = new ChromeDriver();
        driver.get("https://tmdb-discover.surge.sh/");
        driver.manage().window().maximize();

//        Test.selectMovieFilters(driver,"Movie","Action","1900","2024",3.5);
//        Test.movieOrTvShowSearch(driver,"Fantastic Four");
        Test.moviePopularAPI1();
        //Test.dummyApiExample();



    }
}
