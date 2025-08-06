package com.redbus.tests;

import com.redbus.driver.DriverManager;
import com.redbus.pages.BusResultPage;
import com.redbus.pages.HomePage;
import com.redbus.testbase.TestBase;
import org.testng.Assert;
import org.testng.annotations.*;

@Test(groups = "homePage")
public class HomePageTest extends TestBase {
    private HomePage homePage;

    @BeforeClass
    public void initHomePage() {
        homePage = new HomePage(DriverManager.getDriver());
    }

    @Test(priority = 1,description = "Verify home page to load")
    public void verifyHomePageLoad() {
        Assert.assertTrue(homePage.isLogoDisplayed(), "RedBus logo not displayed");
        Assert.assertTrue(homePage.pageTitle(),"Page title is correctly shown");
    }

    @Test(priority = 2,description = "Verify coupons")
    public void offerSection(){
        homePage.offerSection();
        homePage.applyOfferFilter("Bus");
    }

    @Test(priority = 3,description = "Verify testimonial")
    public void testimonialSection(){
        homePage.testiMonials();
    }

    @Test(priority = 4, description = "Verify from city is selected")
    public void verifyFromCitySelected() {
        String city = "Mumbai";
        homePage.enterFromCity(city)
                .selectFromCity(city);
    }

    @Test(priority = 5,description = "Verify to city is selected")
    public void verifyToCitySelected() {
        String city = "Pune";
        homePage.enterToCity(city)
                .selectToCity(city);
    }

    @Test(priority = 6, dependsOnMethods = {"verifyFromCitySelected", "verifyToCitySelected"}, description = "Verify Search button is clicked")
    public void verifySearchSubmit() {
        busResultPage = homePage.searchButtonClick();
        Assert.assertNotNull(busResultPage, "BusResultPage should be initialized after search");
    }
}