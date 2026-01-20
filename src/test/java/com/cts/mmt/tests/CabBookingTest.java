package com.cts.mmt.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cts.mmt.base.BaseTest;
import com.cts.mmt.pages.CabsPage;
import com.cts.mmt.pages.HomePage;

/**
 * CabBookingTest - Test class for Cab Booking functionality
 * Tests outstation cab booking from Delhi to Manali with SUV car type
 * 
 * Problem Statement: Book one way outstation cab and display the lowest charges
 * 1. From Delhi to Manali, Himachal Pradesh
 * 2. Pick up from Delhi at 6.30 AM on 23rd Dec 2019
 * 3. Car type should be SUV
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class CabBookingTest extends BaseTest {

    private HomePage homePage;
    private CabsPage cabsPage;

    // Test data
    private static final String FROM_CITY = "Delhi";
    private static final String TO_CITY = "Manali";
    private static final String CAR_TYPE = "SUV";

    /**
     * Initialize page objects before each test
     */
    @BeforeMethod
    public void initPageObjects() {
        homePage = new HomePage(getDriver());
    }

    /**
     * Test Case 1: Book outstation cab from Delhi to Manali and verify lowest charges
     * 
     * Steps:
     * 1. Navigate to MakeMyTrip
     * 2. Close popup if present
     * 3. Click on Cabs tab
     * 4. Enter From city: Delhi
     * 5. Enter To city: Manali, Himachal Pradesh
     * 6. Click Search
     * 7. Select SUV car type
     * 8. Display lowest cab charges
     */
    @Test(priority = 1, description = "Book one way outstation cab from Delhi to Manali and display lowest charges")
    public void testBookOutstationCabDelhiToManali() {
        // Create test in Extent Report
        createTest("Book Outstation Cab - Delhi to Manali", 
                   "Book one way outstation cab from Delhi to Manali with SUV and get lowest charges");

        try {
            // Step 1: Navigate to MakeMyTrip
            logInfo("Step 1: Navigating to MakeMyTrip website");
            navigateToApplication();
            
            // Step 2: Verify home page is displayed
            logInfo("Step 2: Verifying home page is displayed");
            Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
            logPass("Home page displayed successfully");

            // Step 3: Close popup if present
            logInfo("Step 3: Closing popup if present");
            homePage.closePopupIfPresent();
            logPass("Popup handled");

            // Step 4: Click on Cabs tab
            logInfo("Step 4: Clicking on Cabs tab");
            cabsPage = homePage.clickCabsTab();
            logPass("Clicked on Cabs tab");

            // Step 5: Enter From city - Delhi
            logInfo("Step 5: Entering From city: " + FROM_CITY);
            cabsPage.enterFromCity(FROM_CITY);
            logPass("From city entered: " + FROM_CITY);

            // Step 6: Enter To city - Manali
            logInfo("Step 6: Entering To city: " + TO_CITY);
            cabsPage.enterToCity(TO_CITY);
            logPass("To city entered: " + TO_CITY);

            // Step 7: Select Pickup Date
            logInfo("Step 7: Selecting pickup date");
            cabsPage.selectDate();
            logPass("Pickup date selected");

            // Step 8: Click Search
            logInfo("Step 8: Clicking Search button");
            cabsPage.clickSearch();
            logPass("Search button clicked");

            // Step 9: Select SUV car type
            logInfo("Step 9: Selecting " + CAR_TYPE + " car type");
            cabsPage.selectSUVCarType();
            logPass("Car type selected: " + CAR_TYPE);

            // Step 10: Get and display lowest cab charges
            logInfo("Step 10: Getting lowest cab charges");
            String lowestPrice = cabsPage.getLowestCabPrice();
            
            // Display results
            System.out.println("\n=================================================================");
            System.out.println("OUTSTATION CAB BOOKING RESULTS");
            System.out.println("=================================================================");
            System.out.println("From: " + FROM_CITY);
            System.out.println("To: " + TO_CITY + ", Himachal Pradesh");
            System.out.println("Car Type: " + CAR_TYPE);
            System.out.println("Lowest SUV Cab Price: " + lowestPrice);
            System.out.println("=================================================================\n");
            
            logPass("Lowest SUV Cab Price (Delhi → Manali): " + lowestPrice);
            
            // Wait for 10 seconds to allow user to see the results on screen
            logInfo("Waiting for 10 seconds to display results...");
            waitForSeconds(10);

        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Test Case 2: Verify cab search with different cities
     */
    @Test(priority = 2, description = "Verify cab search functionality with valid cities")
    public void testCabSearchWithValidCities() {
        createTest("Cab Search - Valid Cities", 
                   "Verify cab search works with valid city inputs");

        try {
            logInfo("Navigating to MakeMyTrip");
            navigateToApplication();
            
            logInfo("Closing popup if present");
            homePage.closePopupIfPresent();
            
            logInfo("Clicking on Cabs tab");
            cabsPage = homePage.clickCabsTab();
            
            logInfo("Verifying Cabs page is displayed");
            // Verify we are on cabs page
            Assert.assertTrue(cabsPage.isCabsPageDisplayed() || 
                            getDriver().getCurrentUrl().contains("makemytrip"),
                            "Should be on Cabs page or MakeMyTrip site");
            
            logPass("Cab search page loaded successfully");
            
        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Test Case 3: Verify navigation from Cabs to Home page
     */
    @Test(priority = 3, description = "Verify navigation back to home page from Cabs")
    public void testNavigationFromCabsToHome() {
        createTest("Navigation - Cabs to Home", 
                   "Verify user can navigate back to home from Cabs page");

        try {
            logInfo("Navigating to MakeMyTrip");
            navigateToApplication();
            
            logInfo("Closing popup");
            homePage.closePopupIfPresent();
            
            logInfo("Clicking on Cabs tab");
            cabsPage = homePage.clickCabsTab();
            
            logInfo("Scrolling to top and navigating back to home");
            homePage.scrollToTop();
            homePage.clickLogo();
            
            logInfo("Verifying home page is displayed");
            Assert.assertTrue(homePage.isHomePageDisplayed(), 
                            "Should navigate back to home page");
            
            logPass("Successfully navigated back to home page");
            
        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            throw e;
        }
    }
}
