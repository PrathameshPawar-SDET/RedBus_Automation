package com.redbus.utils;

import com.redbus.driver.DriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    public static String getCurrentDateTime(String pattern){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now().format(dtf);
    }
    public static void scrollintoview(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView({behaior:'smooth'})",element);
    }
}
