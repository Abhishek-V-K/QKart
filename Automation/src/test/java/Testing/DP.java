package Testing;

import org.testng.annotations.DataProvider;

public class DP {

    @DataProvider(name = "movieFiltersData")
    public Object[][] getMovieFiltersData() {
        return new Object[][] {
                {"Movie", "Action", "2000", "2024", 3.5},
                // {"TV Show", "Comedy", "1990", "2020", 4.0},
                // {"Movie", "Drama", "1980", "2024", 2.5}
        };
    }

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return new Object[][] {
                {"Fantastic Four"},
                {"Avengers"},
                {"Breaking Bad"}
        };
    }
}