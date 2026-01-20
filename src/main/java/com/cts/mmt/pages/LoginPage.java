package com.cts.mmt.pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cts.mmt.utils.ConfigReader;

/**
 * LoginPage - Page Object for Login functionality
 * Note: MakeMyTrip uses OAuth/OTP based login, this is a sample structure
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class LoginPage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // ==================== Locators ====================

    // Login/Signup button on header
    @FindBy(xpath = "//li[contains(@class,'login') or contains(@class,'myAccount')]//a | //span[contains(text(),'Login')]")
    private WebElement loginLink;

    // Username/Email field
    @FindBy(xpath = "//input[@type='email' or @type='text'][contains(@placeholder,'Email') or contains(@name,'username')]")
    private WebElement usernameField;

    // Password field
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    // Login button
    @FindBy(xpath = "//button[contains(text(),'Login') or contains(text(),'Sign In') or @type='submit']")
    private WebElement loginButton;

    // Error message
    @FindBy(xpath = "//span[contains(@class,'error') or contains(@class,'invalid')] | //p[contains(@class,'error')]")
    private WebElement errorMessage;

    // Close popup button
    @FindBy(css = "span.commonModal__close")
    private WebElement closePopupButton;

    /**
     * Constructor - Initialize page elements using PageFactory
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInstance().getExplicitWait()));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        logger.info("LoginPage initialized");
    }

    /**
     * Enter username/email
     * @param username Username or email
     */
    public void enterUsername(String username) {
        try {
            WebElement field = wait.until(ExpectedConditions.elementToBeClickable(usernameField));
            field.clear();
            field.sendKeys(username);
            logger.info("Entered username: " + username);
        } catch (Exception e) {
            logger.error("Failed to enter username: " + e.getMessage());
            throw new RuntimeException("Could not enter username");
        }
    }

    /**
     * Enter password
     * @param password Password
     */
    public void enterPassword(String password) {
        try {
            WebElement field = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
            field.clear();
            field.sendKeys(password);
            logger.info("Entered password");
        } catch (Exception e) {
            logger.error("Failed to enter password: " + e.getMessage());
            throw new RuntimeException("Could not enter password");
        }
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            js.executeScript("arguments[0].click();", button);
            logger.info("Clicked Login button");
        } catch (Exception e) {
            logger.error("Failed to click login button: " + e.getMessage());
            throw new RuntimeException("Could not click login button");
        }
    }

    /**
     * Perform login with credentials
     * @param username Username
     * @param password Password
     * @return HomePage instance
     */
    public HomePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        logger.info("Login attempted with username: " + username);
        return new HomePage(driver);
    }

    /**
     * Click on Login link to open login modal
     */
    public void clickLoginLink() {
        try {
            // Close popup if present
            try {
                WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(closePopupButton));
                js.executeScript("arguments[0].click();", popup);
            } catch (Exception e) {
                // No popup
            }

            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
            js.executeScript("arguments[0].click();", link);
            logger.info("Clicked on Login link");
        } catch (Exception e) {
            logger.error("Failed to click login link: " + e.getMessage());
        }
    }

    /**
     * Get error message text
     * @return Error message
     */
    public String getErrorMessage() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOf(errorMessage));
            String message = error.getText();
            logger.info("Error message: " + message);
            return message;
        } catch (Exception e) {
            logger.warn("No error message found");
            return "";
        }
    }

    /**
     * Check if error message is displayed
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if login page/modal is displayed
     * @return true if login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        try {
            return usernameField.isDisplayed() || passwordField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
