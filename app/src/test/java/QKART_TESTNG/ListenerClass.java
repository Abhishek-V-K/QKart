package QKART_TESTNG;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;


public class ListenerClass implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started: " + result.getMethod().getMethodName());
        WebDriver driver = QKART_Tests.driver; // Access the static driver
        QKART_Tests.takeScreenshot(driver, "BeforeTest", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getMethod().getMethodName());
        WebDriver driver = QKART_Tests.driver; // Access the static driver
        QKART_Tests.takeScreenshot(driver, "AfterTest", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getMethod().getMethodName());
        WebDriver driver = QKART_Tests.driver; // Access the static driver
        QKART_Tests.takeScreenshot(driver, "TestFailure", result.getMethod().getMethodName());
    }

    // @Override
    // public void onTestSkipped(ITestResult result) {
    //     System.out.println("Test skipped: " + result.getMethod().getMethodName());
    // }

    // @Override
    // public void onFinish(ITestContext context) {
    //     System.out.println("Test finished: " + context.getName());
    // }

    // @Override
    // public void onStart(ITestContext context) {
    //     System.out.println("Test started: " + context.getName());
    // }

    // @Override
    // public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    //     System.out.println("Test partially succeeded: " + result.getMethod().getMethodName());
    // }
}
