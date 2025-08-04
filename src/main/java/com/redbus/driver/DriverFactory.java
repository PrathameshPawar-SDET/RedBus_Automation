package com.redbus.driver;

import com.redbus.exceptions.InvalidBrowserException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    private DriverFactory(){}

    public static WebDriver createDriver(String browser){
        WebDriver driver = null;

        switch (browser.toLowerCase()){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                throw new InvalidBrowserException("Invalid browser name"+browser);

        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        return driver;
    }
}
