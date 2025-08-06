package com.redbus.pages;

import com.redbus.driver.DriverManager;
import com.redbus.utils.CommonUtils;
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

    @FindBy(xpath = "//div[contains(@class,'offerSection')]")
    private WebElement offerSection;

    @FindBy(xpath = "//button[contains(@class,'tonalInverseButton')]")
    private List<WebElement> couponList;

    @FindBy(xpath = "//h1[contains(@data-autoid,'headerText')]")
    private WebElement headerText;

    @FindBy(xpath = "//div[contains(@class,'testimonialsCards_')]")
    private WebElement testimonialCardSection;

    @FindBy(xpath = "//div[contains(@class,'TestimonialsCard')]")
    private List<WebElement> testimonialCard;



    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isLogoDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(redbusLogo)).isDisplayed();
    }

    public boolean pageTitle() {
        String pageTitle = driver.getTitle();
        return pageTitle.equalsIgnoreCase("Bus Ticket Booking Online at Cheapest Price with Top Bus Operators- redBus");
    }

    public HomePage offerSection() {
        CommonUtils.scrollintoview(offerSection);
        return this;
    }

    public HomePage applyOfferFilter(String filterName) {
        String filterXpath = String.format("//div[contains(@class,'mainContainer_')]//div[contains(text(),'%s')]", filterName);
        WebElement filterLocator = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(filterXpath)));
        filterLocator.click();
        System.out.println("List of coupons");
        for (WebElement coupon : couponList) {
            System.out.println(coupon.getText());
        }
        return this;

    }

    public HomePage testiMonials() {
        int i=1;
        CommonUtils.scrollintoview(testimonialCardSection);
        wait.until(ExpectedConditions.visibilityOfAllElements(testimonialCard));
        System.out.println("Total count of testimonial: "+testimonialCard.size());

        By testimonialText = By.xpath(".//div[contains(@class,'TestimonialText')]");
        By testimonialCustomer = By.xpath(".//div[contains(@class,'CustomerName')]");
        for (WebElement tC: testimonialCard){
            WebElement customerElement =  wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(tC,testimonialCustomer));
            WebElement reviewElement =  wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(tC,testimonialText));
            String customer = customerElement.getText();
            String review = reviewElement.getText();
            System.out.println("Testimonial "+i);
            System.out.println("Customer :"+customer);
            System.out.println("Review :"+review);
            i++;
        }
        return this;

    }

    public HomePage enterFromCity(String city) {
        CommonUtils.scrollintoview(headerText);
        wait.until(ExpectedConditions.elementToBeClickable(fromCity));
        fromCity.click();
        DriverManager.getDriver().switchTo().activeElement().sendKeys(city);
        return this;
    }

    public HomePage selectFromCity(String city) {
        wait.until(ExpectedConditions.visibilityOf(fromSuggestion));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class,'searchCategory')]"), 1));
        for (WebElement name : suggestionHeader) {
            if (name.getText().equalsIgnoreCase(city)) {
                name.click();
                break;
            }
        }
        return this;
    }

    public HomePage enterToCity(String city) {
        wait.until(ExpectedConditions.visibilityOf(toSuggestion));
        DriverManager.getDriver().switchTo().activeElement().sendKeys(city);
        return this;
    }

    public HomePage selectToCity(String city) {
        wait.until(ExpectedConditions.visibilityOf(toSuggestion));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class,'searchCategory')]"), 2));
        for (WebElement name : suggestionHeader) {
            if (name.getText().equalsIgnoreCase(city)) {
                name.click();
                break;
            }
        }
        return this;
    }

    public BusResultPage searchButtonClick() {
        wait.until(ExpectedConditions.elementToBeClickable(searchBusesButton)).click();
        wait.until(ExpectedConditions.urlContains("/bus-tickets/"));
        return new BusResultPage(DriverManager.getDriver());
    }



}
