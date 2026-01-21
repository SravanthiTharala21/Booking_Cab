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
    public void testExtractAdultPersonNumbers() {
        createTest("Hotel - Extract Adult Numbers", 
                   "Extract all adult person numbers from dropdown and store in List");

       

    /**
     * Test Case 2: Navigate to Hotels page and verify display
     */
    public void testHotelsPageDisplay() {
        createTest("Hotel - Page Display", 
                   "Verify Hotels page displays correctly after navigation");

      public void testNavigationFromCabsToHotels() {
        createTest("Navigation - Cabs to Hotels", 
                   "Verify user can navigate from Cabs page to Hotels page");

          public void testCompleteNavigationWorkflow() {
        createTest("Complete Navigation Workflow", 
                   "Navigate through Cabs, then return to Home and go to Hotels");

      
}
