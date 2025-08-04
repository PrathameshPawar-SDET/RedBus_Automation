package com.redbus.tests;

import com.redbus.testbase.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(dependsOnGroups = "homePage")
public class BusResultPageTest extends TestBase {
    @Test(priority = 1, description = "Verify initial bus count is loaded")
    public void busCount() {
        Assert.assertNotNull(busResultPage, "busResultPage is NULL. Ensure HomePageTest.verifySearchSubmit ran.");
        busResultPage.getBusCount();
    }

    @Test(priority = 2, description = "Verify list of bus name displayed after applying filter")
    public void getBusName(){
        busResultPage.applyFilters("Primo");
        busResultPage.applyFilters("AC");
        busResultPage.getBusNames();

    }
}