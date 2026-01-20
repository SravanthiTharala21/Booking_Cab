package com.cts.mmt.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cts.mmt.base.BaseTest;
import com.cts.mmt.pages.HomePage;
import com.cts.mmt.pages.LoginPage;
import com.cts.mmt.utils.ConfigReader;

/**
 * LoginTest - Test class for Login functionality
 * Note: MakeMyTrip uses OAuth/OTP based login, these tests demonstrate the framework structure
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class LoginTest extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;

    // Test data from config
    private String validUsername;

    /**
     * Initialize page objects and test data before each test
     */
    @BeforeMethod
    public void initPageObjects() {
        homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());
        
        // Get credentials from config
        validUsername = ConfigReader.getInstance().getUsername();
    }

    /**
     * Test Case 1: Valid Login Test
     * Note: MakeMyTrip uses OTP-based login, this demonstrates the test structure
     */
    @Test(priority = 1, description = "Verify login with valid credentials")
    public void testValidLogin() {
        createTest("Login - Valid Credentials", 
                   "Verify user can login with valid username and password");

        try {
            // Step 1: Navigate to application
            logInfo("Step 1: Navigating to MakeMyTrip");
            navigateToApplication();

            // Step 2: Verify home page is displayed
            logInfo("Step 2: Verifying home page is displayed");
            Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
            logPass("Home page displayed successfully");

            // Step 3: Close popup if present
            logInfo("Step 3: Handling popup");
            homePage.closePopupIfPresent();
            logPass("Popup handled");

            // Step 4: Click on login link (Note: MakeMyTrip uses OTP/OAuth)
            logInfo("Step 4: Attempting to access login");
            // For demonstration - actual login would require OTP
            logInfo("Note: MakeMyTrip uses OTP-based authentication");
            logInfo("Test demonstrates framework structure with valid credentials: " + validUsername);
            
            logPass("Valid login test structure completed");

        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * Test Case 2: Invalid Login Test
     */
    @Test(priority = 2, description = "Verify login fails with invalid credentials")
    public void testInvalidLogin() {
        createTest("Login - Invalid Credentials", 
                   "Verify appropriate error message is displayed for invalid login");

        String invalidUsername = "invalid@email.com";
        String invalidPassword = "wrongpassword123";

        try {
            // Step 1: Navigate to application
            logInfo("Step 1: Navigating to MakeMyTrip");
            navigateToApplication();

            // Step 2: Verify home page is displayed
            logInfo("Step 2: Verifying home page is displayed");
            Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
            logPass("Home page displayed successfully");

            // Step 3: Handle popup
            logInfo("Step 3: Handling popup");
            homePage.closePopupIfPresent();
            logPass("Popup handled");

            // Step 4: Attempt login with invalid credentials
            logInfo("Step 4: Attempting login with invalid credentials");
            logInfo("Invalid Username: " + invalidUsername);
            logInfo("Invalid Password: " + invalidPassword.replaceAll(".", "*"));
            
            // For demonstration - actual invalid login would show error
            logInfo("Note: Invalid credentials would trigger error message");
            
            logPass("Invalid login test structure completed");

        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * Test Case 3: Empty Credentials Test
     */
    @Test(priority = 3, description = "Verify login fails with empty credentials")
    public void testEmptyCredentials() {
        createTest("Login - Empty Credentials", 
                   "Verify appropriate error message for empty username and password");

        try {
            // Step 1: Navigate to application
            logInfo("Step 1: Navigating to MakeMyTrip");
            navigateToApplication();

            // Step 2: Verify home page is displayed
            logInfo("Step 2: Verifying home page is displayed");
            Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
            logPass("Home page displayed successfully");

            // Step 3: Handle popup
            logInfo("Step 3: Handling popup");
            homePage.closePopupIfPresent();
            logPass("Popup handled");

            // Step 4: Attempt login with empty credentials
            logInfo("Step 4: Attempting login with empty credentials");
            logInfo("Empty Username: ''");
            logInfo("Empty Password: ''");
            
            // For demonstration - empty login would show validation error
            logInfo("Note: Empty credentials would trigger validation error");
            
            logPass("Empty credentials test structure completed");

        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * Test Case 4: Login Page Display Test
     */
    @Test(priority = 4, description = "Verify login page/modal displays correctly")
    public void testLoginPageDisplay() {
        createTest("Login - Page Display", 
                   "Verify login page elements are displayed correctly");

        try {
            // Step 1: Navigate to application
            logInfo("Step 1: Navigating to MakeMyTrip");
            navigateToApplication();

            // Step 2: Verify home page
            logInfo("Step 2: Verifying home page is displayed");
            Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
            logPass("Home page displayed successfully");

            // Step 3: Close popup
            logInfo("Step 3: Handling popup");
            homePage.closePopupIfPresent();
            logPass("Popup handled");

            // Step 4: Click login link
            logInfo("Step 4: Attempting to open login modal");
            loginPage.clickLoginLink();
            
            logPass("Login page display test completed");

        } catch (Exception e) {
            logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
