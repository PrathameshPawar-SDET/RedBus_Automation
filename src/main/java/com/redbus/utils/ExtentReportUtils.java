package com.redbus.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.redbus.driver.DriverManager;

import java.util.HashMap;
import java.util.Map;

public class ExtentReportUtils {

    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> extentTestMap = new HashMap<>();
    static String  dateformat = CommonUtils.getCurrentDateTime("yyyyMMdd_HHmmss");
    private static final String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport/"
            +dateformat+".html";

    public static ExtentReports initExtentReport(){
        if(extent==null){
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("RedBus Automation Report");
            sparkReporter.config().setReportName("RedBus Test Automation");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setEncoding("UTF-8");
            sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Organization","RedBus");
            extent.setSystemInfo("Automation Framework","Selenium WebDriver");
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("OS",System.getProperty("os.name"));

        }
        return extent;
    }

    public static ExtentTest createTest(String testname, String description){
        ExtentTest test = extent.createTest(testname, description);
        extentTestMap.put(Thread.currentThread().getId(),test);
        return test;
    }
    public static ExtentTest getTest(){
        return extentTestMap.get(Thread.currentThread().getId());
    }
    public static void addScreenshot(String caption){
        String screenshotpath=ScreenshotUtils.captureScreenshot(DriverManager.getDriver(),"screenshot_"+CommonUtils.getCurrentDateTime("HHmmss"));

        try {
            getTest().addScreenCaptureFromPath(screenshotpath,caption);
        }
        catch (Exception e){
            getTest().warning("Failed to attach screenshot"+e.getMessage());
        }
    }

    public static void flushReport(){
        if(extent!=null){
            extent.flush();
        }
    }
}
