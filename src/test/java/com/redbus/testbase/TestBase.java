package com.redbus.testbase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.redbus.driver.DriverFactory;
import com.redbus.driver.DriverManager;
import com.redbus.pages.BusResultPage;
import com.redbus.utils.ExtentReportUtils;
import com.redbus.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

public class TestBase {
    protected static ExtentReports extent;
    protected ExtentTest test;
    protected static WebDriver driver;
    protected static BusResultPage busResultPage;

    @BeforeSuite
    @Parameters("browser")
    public void setUpSuite(String browser) {
        extent = ExtentReportUtils.initExtentReport();
        if (driver == null) {
            driver = DriverFactory.createDriver(browser);
            DriverManager.setDriver(driver); // Keep DriverManager in sync
            driver.get("https://www.redbus.in/");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

//    @BeforeClass
//    @Parameters("browser")
//    public void setupClass(String browser) {
//        if (driver == null) {
//            driver = DriverFactory.createDriver(browser);
//            DriverManager.setDriver(driver); // Keep DriverManager in sync
//            driver.get("https://www.redbus.in/");
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        }
//    }

    @BeforeMethod
    public void setupMethod(Method method) {
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Case Failed: " + result.getName());
            test.log(Status.FAIL, "Error: " + result.getThrowable());
            String screenshotpath = ScreenshotUtils.captureScreenshot(driver, result.getName());
            test.addScreenCaptureFromPath(screenshotpath);
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case Skipped: " + result.getName());
        } else {
            test.log(Status.PASS, "Test Case Passed: " + result.getName());
        }
    }

//    @AfterClass
//    public void tearDownClass() {
//        if (driver != null) {
//            driver.quit();
//            driver = null;
//            DriverManager.unload();
//        }
//    }

    @AfterSuite
    public void tearDownSuite() {
//        if (driver != null) {
//            driver.quit();
//            driver = null;
//            DriverManager.unload();
//        }
        extent.flush();

    }
}
