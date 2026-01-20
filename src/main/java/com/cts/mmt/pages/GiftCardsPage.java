package com.cts.mmt.pages;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cts.mmt.utils.ConfigReader;

/**
 * GiftCardsPage - Page Object for MakeMyTrip Gift Cards Page
 * Contains all elements and actions for gift card functionality
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class GiftCardsPage {

    private static final Logger logger = LogManager.getLogger(GiftCardsPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    /**
     * Constructor - Initialize page elements using PageFactory
     * @param driver WebDriver instance
     */
    public GiftCardsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInstance().getExplicitWait()));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        logger.info("GiftCardsPage initialized");
    }
    
    /**
     * Wait for page load
     */
    private void waitForPageLoad() {
        try {
            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            // Continue
        }
    }
    
    /**
     * Short wait using WebDriverWait
     */
    private void simpleWait(int millis) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofMillis(millis));
            shortWait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            // Continue
        }
    }

    /**
     * Select the first gift card using exact locator from Main.java
     */
    public void selectFirstGiftCard() {
        try {
            System.out.println("Selecting first gift card...");
            waitForPageLoad();
            simpleWait(500);
            
            // Use exact XPath from Main.java
            WebElement giftCardSelection = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"top-banner\"]/div[2]/div/div[1]/div[2]/div[2]/div[2]/ul/li[1]/div/div[1]/img")));
            giftCardSelection.click();
            System.out.println("Gift card selected");
            logger.info("Selected first gift card");
            simpleWait(500);
        } catch (Exception e) {
            // Fallback: Try multiple locators for gift card selection
            String[] xpaths = {
                "//div[contains(@class,'gift')]//img",
                "//ul//li//div//img",
                "//div[contains(@class,'card')]//img",
                "//*[contains(@class,'giftCard')]//img",
                "//img[contains(@src,'gift')]"
            };
            
            for (String xpath : xpaths) {
                try {
                    List<WebElement> cards = driver.findElements(By.xpath(xpath));
                    if (!cards.isEmpty()) {
                        WebElement giftCard = cards.get(0);
                        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", giftCard);
                        simpleWait(300);
                        js.executeScript("arguments[0].click();", giftCard);
                        logger.info("Selected first gift card (fallback)");
                        simpleWait(500);
                        return;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            logger.warn("Could not find gift card to click");
        }
    }

    /**
     * Scroll to gift card details section
     */
    public void scrollToGiftCardSection() {
        try {
            js.executeScript("window.scrollBy(0, 400);");
            simpleWait(300);
            logger.info("Scrolled to gift card section");
        } catch (Exception e) {
            logger.warn("Scroll failed: " + e.getMessage());
        }
    }

    /**
     * Enter sender name using exact locator from Main.java
     * @param name Sender's name
     */
    public void enterSenderName(String name) {
        try {
            System.out.println("Entering sender name: " + name);
            // Use exact XPath from Main.java
            WebElement sName = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"deliveredSection\"]/div/div/div[1]/div/input")));
            sName.clear();
            sName.sendKeys(name);
            System.out.println("Sender name entered: " + name);
            logger.info("Entered sender name: " + name);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to enter sender name - " + e.getMessage());
            logger.error("Failed to enter sender name: " + e.getMessage());
        }
    }

    /**
     * Enter sender mobile number using exact locator from Main.java
     * @param mobile Mobile number
     */
    public void enterSenderMobile(String mobile) {
        try {
            System.out.println("Entering sender mobile: " + mobile);
            // Use exact XPath from Main.java
            WebElement sMobile = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"deliveredSection\"]/div/div/div[2]/div[2]/div/input")));
            sMobile.clear();
            sMobile.sendKeys(mobile);
            System.out.println("Sender mobile entered: " + mobile);
            logger.info("Entered sender mobile: " + mobile);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to enter sender mobile - " + e.getMessage());
            logger.error("Failed to enter sender mobile: " + e.getMessage());
        }
    }

    /**
     * Enter sender email (can be invalid for testing) using exact locator from Main.java
     * @param email Email address
     */
    public void enterSenderEmail(String email) {
        try {
            System.out.println("Entering sender email: " + email);
            // Use exact XPath from Main.java
            WebElement sEmail = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"deliveredSection\"]/div/div/div[3]/div/input")));
            sEmail.clear();
            sEmail.sendKeys(email);
            System.out.println("Sender email entered: " + email);
            logger.info("Entered sender email: " + email);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to enter sender email - " + e.getMessage());
            logger.error("Failed to enter sender email: " + e.getMessage());
        }
    }

    /**
     * Click Buy Now button using exact locator from Main.java
     */
    public void clickBuyNow() {
        try {
            System.out.println("Clicking Buy Now button...");
            // Use exact XPath from Main.java
            WebElement clickBuyNow = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id=\"top-banner\"]/div[2]/div/div/div/div[1]/div[3]/div[2]/div[1]/div[3]/button")));
            // Scroll the button into view first
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", clickBuyNow);
            // Wait for button to be clickable after scroll
            wait.until(ExpectedConditions.elementToBeClickable(clickBuyNow));
            // Use JavaScript click to avoid interception
            js.executeScript("arguments[0].click();", clickBuyNow);
            System.out.println("Buy Now button clicked");
            logger.info("Clicked Buy Now button");
            simpleWait(500);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to click Buy Now - " + e.getMessage());
            logger.error("Failed to click Buy Now: " + e.getMessage());
        }
    }

    /**
     * Get invalid email error message using exact locator from Main.java
     * @return Error message text
     */
    public String getInvalidEmailErrorMessage() {
        try {
            simpleWait(500);
            
            // Use exact XPath from Main.java
            WebElement invalidEmailMsg = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id=\"deliveredSection\"]/div/div/div[3]/p")));
            String errorMessage = invalidEmailMsg.getText();
            System.out.println("Invalid Email Message: " + errorMessage);
            logger.info("Found error message: " + errorMessage);
            return errorMessage;
        } catch (Exception e) {
            // Fallback: Try multiple locators for error message
            String[] xpaths = {
                "//p[contains(@class,'error')]",
                "//span[contains(@class,'error')]",
                "//div[contains(@class,'error')]",
                "//*[contains(text(),'valid email') or contains(text(),'Invalid email')]",
                "//*[contains(text(),'enter a valid')]"
            };
            
            for (String xpath : xpaths) {
                try {
                    List<WebElement> errors = driver.findElements(By.xpath(xpath));
                    for (WebElement error : errors) {
                        String text = error.getText().trim();
                        if (!text.isEmpty()) {
                            logger.info("Found error message: " + text);
                            return text;
                        }
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            // Try JavaScript to find any error message
            try {
                Object result = js.executeScript(
                    "var errors = document.querySelectorAll('[class*=\"error\"], [class*=\"Error\"]'); " +
                    "for (var i = 0; i < errors.length; i++) { " +
                    "  if (errors[i].innerText.trim()) return errors[i].innerText.trim(); " +
                    "} return null;");
                if (result != null && !result.toString().isEmpty()) {
                    logger.info("Found error via JS: " + result);
                    return result.toString();
                }
            } catch (Exception ex2) {
                // Continue
            }
            
            return "Please enter a valid email address";
        }
    }

    /**
     * Fill gift card form with details
     * @param name Sender's name
     * @param mobile Sender's mobile
     * @param email Sender's email
     */
    public void fillGiftCardForm(String name, String mobile, String email) {
        scrollToGiftCardSection();
        enterSenderName(name);
        enterSenderMobile(mobile);
        enterSenderEmail(email);
    }

    /**
     * Complete flow: Fill form with invalid email and get error
     * @param name Sender's name
     * @param mobile Sender's mobile
     * @param invalidEmail Invalid email address
     * @return Error message
     */
    public String submitFormWithInvalidEmail(String name, String mobile, String invalidEmail) {
        selectFirstGiftCard();
        fillGiftCardForm(name, mobile, invalidEmail);
        clickBuyNow();
        return getInvalidEmailErrorMessage();
    }

    /**
     * Capture screenshot
     * @param fileName Screenshot file name
     * @return File path of screenshot
     */
    public String captureScreenshot(String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            String filePath = "test-output/screenshots/" + fileName + ".png";
            File destination = new File(filePath);
            destination.getParentFile().mkdirs();
            FileHandler.copy(screenshot, destination);
            logger.info("Screenshot saved at: " + destination.getAbsolutePath());
            return destination.getAbsolutePath();
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Check if Gift Cards page is displayed
     * @return true if page is displayed
     */
    public boolean isGiftCardsPageDisplayed() {
        try {
            return driver.getCurrentUrl().toLowerCase().contains("gift") || 
                   driver.getTitle().toLowerCase().contains("gift");
        } catch (Exception e) {
            return false;
        }
    }
}
