package QKART_TESTNG;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.Table.Cell;
import org.testng.annotations.DataProvider;


public class DP {
    @DataProvider(name = "data-provider")
    public Object[][] dpMethod(Method m) throws IOException {
        switch (m.getName()) {
            case "TestCase01":
                return new Object[][] {{"testUsername", "testPassword"},
                        {"testUsername123", "testPassword123"}, {"test_123", "test_123"}};
            case "TestCase02":
                return new Object[][] {{"testUsername", "testPassword"},
                        {"testUsername123", "testPassword123"}, {"test_123", "test_123"}};
            case "TestCase03":
                return new Object[][] {{"testUsername", "testPassword"}, {"YONEX"}, {"Roadster"},
                        {"Connector"}};
            case "TestCase04":
                return new Object[][] {{"testUsername", "testPassword"},
                        {"Nike Mens Running Shoes"}, {"Roadster"}, {"HandBag"}};
            case "TestCase05":
                return new Object[][] {{"testUsername", "testPassword"},
                        {"YONEX Smash Badminton Racquet", "Tan Leatherette Weekender Duffle",
                                "Addr line 1 addr Line 2 addr line 3"},
                        {"Tan Leatherette Weekender Duffle", "YONEX Smash Badminton Racquet",
                                "TEST ADDR LINES COUNT GREATER TH"},
                        {"YONEX Smash Badminton Racquet", "Connector",
                                "1 Hacker Way Menlo Park, CA 94025"}};
            case "TestCase06":
                return new Object[][] {{"Xtend Smart Watch", "Yarine Floor Lamp"},
                        {"Yarine Floor Lamp", "Connector"}, {"Connector", "Kindle"}};
            case "TestCase07":
                return new Object[][] {{"Tan Leatherette Weekender Duffle;Xtend Smart Watch"},
                        {"Connector;Xtend Smart Watch"}, {"Kindle;Jenga"}};
            case "TestCase08":
                return new Object[][] {{"Tan Leatherette Weekender Duffle","60"},
                        {"SuitCase","50"}, {"Jenga","60"}};
            case "TestCase11":
                return new Object[][] {{"crio user","criouser@gmail.com","Testing the contact us page"},
                        {"facebook user","test_user__@gmail.com","!!!special characters!!"}, {"hacker user !!!","bad_user@gmail.com","<XSS testing>"}};
            case "TestCase12":
                return new Object[][] {{"YONEX Smash Badminton Racquet","Addr line 1 addr Line 2 addr line 3"},
                        {"Connector","Apple Park, Cupertino, CA, U.S. - CA"}, {"Kindle","Dubai main road , Near Bus Stand"}};
            
        }
        return null;

    }
}
