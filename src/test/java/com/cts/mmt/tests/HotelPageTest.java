package com.cts.mmt.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cts.mmt.base.BaseTest;
import com.cts.mmt.pages.CabsPage;
import com.cts.mmt.pages.HomePage;
import com.cts.mmt.pages.HotelsPage;

/**
 * HotelPageTest - Test class for Hotels Page functionality
 * Tests extraction of adult person numbers from dropdown
 * 
 * Problem Statement: On the Hotel booking page, extract all the numbers for 
 * Adult persons and store in a List; Display the same
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class HotelPageTest extends BaseTest {

    private HomePage homePage;
    private HotelsPage hotelsPage;
    @SuppressWarnings("unused")
    private CabsPage cabsPage;

    /**
     * Initialize page objects before each test
     */
    @BeforeMethod
    public void initPageObjects() {
        homePage = new HomePage(getDriver());
    }

    /**
     * Test Case 1: Extract adult person numbers from hotel booking dropdown
     * 
     * Steps:
     * 1. Navigate to MakeMyTrip
     * 2. Close popup if present
     * 3. Click on Hotels tab
     * 4. Open Rooms & Guests dropdown
     * 5. Extract all adult person numbers
     * 6. Store in a List
     * 7. Display the list
     */
    @Test(priority = 1, description = "Extract adult person numbers from hotel booking and store in List")
    public void testExtractAdultPersonNumbers() {
        createTest("Hotel - Extract Adult Numbers", 
                   "Extract all adult person numbers from dropdown and store in List");

        try {
            // Step 1: Navigate to MakeMyTrip
            logInfo("Step 1: Navigating to MakeMyTrip website");
            navigateToApplication();

            // Step 2: Verify home page
            logInfo("Step 2: Verifying home page is displayed");
            Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
            logPass("Home page displayed successfully");

            // Step 3: Close popup if present
            logInfo("Step 3: Closing popup if present");
            homePage.closePopupIfPresent();
            logPass("Popup handled");

            // Step 4: Click on Hotels tab
            logInfo("Step 4: Clicking on Hotels tab");
            hotelsPage = homePage.clickHotelsTab();
            logPass("Clicked on Hotels tab");

            // Wait for page to load using explicit wait
            waitForPageLoad();

            // Step 5: Extract adult person numbers
            logInfo("Step 5: Extracting adult person numbers from dropdown");
            List<Integer> adultNumbers = hotelsPage.extractAdultPersonNumbers();
            
            // Verify list is not empty
            Assert.assertNotNull(adultNumbers, "Adult numbers list should not be null");
            Assert.assertFalse(adultNumbers.isEmpty(), "Adult numbers list should not be empty");
            
            // Step 6: Display the list
            logInfo("Step 6: Displaying adult person numbers");
            hotelsPage.displayAdultNumbers(adultNumbers);
            
            // Display results
            System.out.println("\n=================================================================");
            System.out.println("HOTEL BOOKING - ADULT PERSON NUMBERS");
            System.out.println("=================================================================");
            System.out.println("Total adult options found: " + adultNumbers.size());
            System.out.println("Adult numbers list: " + adultNumbers);
            System.out.println("=================================================================\n");
            
            logPass("Successfully extracted " + adultNumbers.size() + " adult person numbers");
            logPass("Adult numbers: " + adultNumbers.toString());

        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * Test Case 2: Navigate to Hotels page and verify display
     */
    @Test(priority = 2, description = "Verify Hotels page is displayed correctly")
    public void testHotelsPageDisplay() {
        createTest("Hotel - Page Display", 
                   "Verify Hotels page displays correctly after navigation");

        try {
            logInfo("Navigating to MakeMyTrip");
            navigateToApplication();
            
            logInfo("Closing popup");
            homePage.closePopupIfPresent();
            
            logInfo("Clicking on Hotels tab");
            hotelsPage = homePage.clickHotelsTab();
            
            // Wait for page to load using explicit wait
            waitForPageLoad();
            
            logInfo("Verifying Hotels page");
            String title = hotelsPage.getPageTitle();
            System.out.println("Hotels page title: " + title);
            
            logPass("Hotels page displayed successfully");

        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * Test Case 3: Navigate from Cabs to Hotels (cross-navigation)
     */
    @Test(priority = 3, description = "Verify navigation from Cabs page to Hotels page")
    public void testNavigationFromCabsToHotels() {
        createTest("Navigation - Cabs to Hotels", 
                   "Verify user can navigate from Cabs page to Hotels page");

        try {
            logInfo("Navigating to MakeMyTrip");
            navigateToApplication();
            
            logInfo("Closing popup");
            homePage.closePopupIfPresent();
            
            logInfo("Clicking on Cabs tab first");
            cabsPage = homePage.clickCabsTab();
            
            // Wait for cabs page to load
            waitForPageLoad();
            
            logInfo("Now navigating to Hotels tab");
            homePage.scrollToTop();
            hotelsPage = homePage.clickHotelsTab();
            
            // Wait for hotels page to load
            waitForPageLoad();
            
            logPass("Successfully navigated from Cabs to Hotels");

        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * Test Case 4: Complete workflow - Cabs, Gift Cards, Hotels navigation
     */
    @Test(priority = 4, description = "Verify complete navigation workflow through multiple pages")
    public void testCompleteNavigationWorkflow() {
        createTest("Complete Navigation Workflow", 
                   "Navigate through Cabs, then return to Home and go to Hotels");

        try {
            // Navigate to MakeMyTrip
            logInfo("Navigating to MakeMyTrip");
            navigateToApplication();
            
            logInfo("Closing popup");
            homePage.closePopupIfPresent();
            
            // Go to Cabs
            logInfo("Step 1: Navigating to Cabs");
            cabsPage = homePage.clickCabsTab();
            waitForPageLoad();
            logPass("On Cabs page");
            
            // Scroll to top and go to Hotels
            logInfo("Step 2: Scrolling up and navigating to Hotels");
            homePage.scrollToTop();
            waitForPageLoad();
            
            hotelsPage = homePage.clickHotelsTab();
            waitForPageLoad();
            logPass("On Hotels page");
            
            // Extract adult numbers
            logInfo("Step 3: Extracting adult numbers");
            List<Integer> adultNumbers = hotelsPage.extractAdultPersonNumbers();
            System.out.println("Adult numbers from Hotels page: " + adultNumbers);
            logPass("Adult numbers extracted: " + adultNumbers);
            
            logPass("Complete navigation workflow successful");

        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
