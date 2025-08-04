package com.redbus.pages;

import com.redbus.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//img[contains(@class,'rbLogo')]")
    private WebElement redbusLogo;

    @FindBy(xpath = "//div[contains(@class,'srcDestWrapper')]")
    private WebElement fromCity;

    @FindBy(xpath = "//div[contains(@class,'suggestionsWrapper')]")
    private WebElement fromSuggestion;

    @FindBy(xpath = "//div[contains(@class,'listHeader')]")
    private List<WebElement> suggestionHeader;

    @FindBy(xpath = "//div[contains(@class,'srcDestWrapper')]")
    private WebElement toCity;

    @FindBy(xpath = "//div[contains(@class,'suggestionsWrapper')]")
    private WebElement toSuggestion;

    @FindBy(xpath = "//button[contains(@class,'searchButtonWrapper')]")
    private WebElement searchBusesButton;


    public HomePage(WebDriver driver){
        this.driver =driver;
        this.wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

    public boolean isLogoDisplayed(){
        return wait.until(ExpectedConditions.visibilityOf(redbusLogo)).isDisplayed();
    }

    public HomePage enterFromCity(String city){
        wait.until(ExpectedConditions.elementToBeClickable(fromCity));
        fromCity.click();
        DriverManager.getDriver().switchTo().activeElement().sendKeys(city);
        return this;
    }

    public HomePage selectFromCity(String city){
        wait.until(ExpectedConditions.visibilityOf(fromSuggestion));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class,'searchCategory')]"),1));
        for(WebElement name:suggestionHeader){
            if(name.getText().equalsIgnoreCase(city)){
                name.click();
                break;
            }
        }
        return this;
    }

    public HomePage enterToCity(String city){
        wait.until(ExpectedConditions.visibilityOf(toSuggestion));
        DriverManager.getDriver().switchTo().activeElement().sendKeys(city);
        return this;
    }

    public HomePage selectToCity(String city){
        wait.until(ExpectedConditions.visibilityOf(toSuggestion));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class,'searchCategory')]"),2));
        for(WebElement name:suggestionHeader){
            if(name.getText().equalsIgnoreCase(city)){
                name.click();
                break;
            }
        }
        return this;
    }

    public BusResultPage searchButtonClick(){
        wait.until(ExpectedConditions.elementToBeClickable(searchBusesButton)).click();
        wait.until(ExpectedConditions.urlContains("/bus-tickets/"));
        return new BusResultPage(DriverManager.getDriver());
    }
}
