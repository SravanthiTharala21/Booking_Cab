package com.cts.mmt.tests;

import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cts.mmt.base.BaseTest;
import com.cts.mmt.pages.GiftCardsPage;
import com.cts.mmt.pages.HomePage;

/**
 * GiftCardTest - Test class for Gift Cards functionality
 * Tests gift card form with invalid email validation
 * 
 * Problem Statement: Find Group Gifting in Gift Cards, fill card details & 
 * give invalid email; capture & display the error message
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class GiftCardTest extends BaseTest {

    private HomePage homePage;
    private GiftCardsPage giftCardsPage;

    // Test data
    private static final String SENDER_NAME = "Sravanthi Tharala";
    private static final String SENDER_MOBILE = "9876543676";
    private static final String INVALID_EMAIL = "sravanthitharala@";
    private static final String VALID_EMAIL = "sravanthi.tharala@example.com";

    /**
     * Initialize page objects before each test
     */
    @BeforeMethod
    public void initPageObjects() {
       

    /**
     * Test Case 1: Fill gift card form with invalid email and verify error message
     * 
     * Steps:
     * 1. Navigate to MakeMyTrip
     * 2. Close popup if present
     * 3. Navigate to Gift Cards via More menu
     * 4. Select a gift card
     * 5. Scroll down to gift card section
     * 6. Fill sender name
     * 7. Fill sender mobile
     * 8. Fill invalid email
     * 9. Click Buy Now
     * 10. Capture and display error message
     * 11. Take screenshot
     */
    @Test(priority = 1, description = "Fill gift card form with invalid email and capture error message")
    public void testGiftCardWithInvalidEmail() {
        createTest("Gift Card - Invalid Email Validation", 
                   "Fill gift card form with invalid email and verify error message is displayed");

        
    public void testNavigateToGiftCardsPage() {
        createTest("Navigation - Gift Cards Page", 
                   "Verify user can navigate to Gift Cards page via More menu");

        
    public void testGiftCardFormWithValidData() {
        createTest("Gift Card - Valid Form Data", 
                   "Fill gift card form with valid email and verify no error");

       
}
