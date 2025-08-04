package com.redbus.pages;

import com.redbus.driver.DriverManager;
import com.redbus.utils.CommonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BusResultPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//div[contains(@class,'searchTopSection')]//span[contains(text(),'buses')]")
    private WebElement busCount;

    @FindBy(xpath = "//div[contains(@class,'travelsName')]")
    private List<WebElement> travelsName;

    @FindBy(xpath = "//li[contains(@class,'tupleWrapper')]")
    private List<WebElement> tupleWrapper;

    @FindBy(xpath = "//span[contains(@class,'endText')]")
    private List<WebElement> endList;

    public BusResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void getBusCount() {
        wait.until(ExpectedConditions.visibilityOf(busCount));
        String count = busCount.getText();
        System.out.println("Bus Count :"+count);
    }

    public void applyFilters(String filterValue){
        String filterXpath = String.format("//div[contains(@aria-label,'%s')]",filterValue);
        WebElement filterButton = driver.findElement(By.xpath(filterXpath));
        filterButton.click();
        wait.until(ExpectedConditions.visibilityOf(busCount));
        String count = busCount.getText();
        System.out.println("Bus count after applying "+filterValue+"filter :"+count);
    }

    public void getBusNames(){
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        while (true){
            if(!endList.isEmpty()){
                break;
            }
            CommonUtils.scrollintoview(tupleWrapper.get(tupleWrapper.size()-3));
        }

        for(WebElement tName:travelsName){
            System.out.println(tName.getText());
        }
        System.out.println("Total Bus :"+travelsName.size());

    }
}
