package com.redbus.utils;

import com.redbus.exceptions.FrameworkException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {
    public static String captureScreenshot(WebDriver driver, String screenshotName){
        String dateformat = CommonUtils.getCurrentDateTime("yyyyMMdd_HHmmss");
        String screenshotPath = System.getProperty("user.dir")+"/test-output/Screenshots/"+screenshotName+"_"+dateformat+".png";
        try {
            File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(sourceFile,new File(screenshotPath));
            return screenshotPath;
        }catch (IOException e){
            throw new FrameworkException("Failed to capture screenshor :"+ e.getMessage(),e);
        }

    }
}
