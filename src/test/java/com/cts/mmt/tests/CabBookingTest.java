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
       
        createTest("Cab Search - Valid Cities", 
                   "Verify cab search works with valid city inputs");

        
    public void testNavigationFromCabsToHome() {
        createTest("Navigation - Cabs to Home", 
                   "Verify user can navigate back to home from Cabs page");

       
}
