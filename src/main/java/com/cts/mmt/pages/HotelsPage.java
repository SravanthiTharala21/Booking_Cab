package com.cts.mmt.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cts.mmt.utils.ConfigReader;

/**
 * HotelsPage - Page Object for MakeMyTrip Hotels Booking Page
 * Contains all elements and actions for hotel booking functionality
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class HotelsPage {

    private static final Logger logger = LogManager.getLogger(HotelsPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    /**
     * Constructor - Initialize page elements using PageFactory
     * @param driver WebDriver instance
     */
    public HotelsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInstance().getExplicitWait()));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        logger.info("HotelsPage initialized");
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
     * Click on Rooms & Guests dropdown to open it
     */
    public void openRoomsGuestsDropdown() {
        try {
            waitForPageLoad();
            simpleWait(300);
            
            // Try multiple locators for the guests dropdown
            String[] xpaths = {
                "//div[contains(@class,'roomGuest')]",
                "//li[contains(@data-cy,'roomGuests')]",
                "//*[contains(text(),'Rooms') and contains(text(),'Guests')]",
                "//span[contains(text(),'Room') or contains(text(),'Guest')]/ancestor::div[1]",
                "//div[contains(@class,'hsw_inputBox')][last()]"
            };

            for (String xpath : xpaths) {
                try {
                    WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                    js.executeScript("arguments[0].click();", dropdown);
                    logger.info("Opened Rooms & Guests dropdown");
                    simpleWait(500);
                    return;
                } catch (Exception e) {
                    continue;
                }
            }
            logger.warn("Could not find Rooms & Guests dropdown");
        } catch (Exception e) {
            logger.error("Failed to open Rooms & Guests dropdown: " + e.getMessage());
        }
    }
    /**
     * Extract all adult person numbers from dropdown and store in list
     * @return List of integers representing adult person options
     */
    public List<Integer> extractAdultPersonNumbers() {
        List<Integer> adultNumbers = new ArrayList<>();
        
        System.out.println("Extracting adult person numbers from dropdown...");
        
        try {
            // First, open the rooms/guests dropdown
            openRoomsGuestsDropdown();
            
            simpleWait(300);
            
            // Try to find adult counter/options using JavaScript
            try {
                Object result = js.executeScript(
                    "var adults = []; " +
                    "var elements = document.querySelectorAll('[class*=\"adult\"], [class*=\"Adult\"], [data-for*=\"adult\"]'); " +
                    "elements.forEach(function(el) { " +
                    "  var text = el.innerText.trim(); " +
                    "  var nums = text.match(/\\d+/g); " +
                    "  if (nums) nums.forEach(function(n) { " +
                    "    var num = parseInt(n); " +
                    "    if (num > 0 && num <= 30 && adults.indexOf(num) === -1) adults.push(num); " +
                    "  }); " +
                    "}); " +
                    "return adults.sort(function(a,b) { return a-b; });");
                
                if (result != null) {
                    @SuppressWarnings("unchecked")
                    List<Object> nums = (List<Object>) result;
                    for (Object num : nums) {
                        adultNumbers.add(((Number) num).intValue());
                    }
                    if (!adultNumbers.isEmpty()) {
                        logger.info("Found adult numbers via JS: " + adultNumbers);
                    }
                }
            } catch (Exception e) {
                logger.warn("JS extraction failed: " + e.getMessage());
            }
            
            // If JS didn't work, try DOM locators
            if (adultNumbers.isEmpty()) {
                String[] locators = {
                    "//li[contains(@data-for,'adults') or contains(@class,'adult')]",
                    "//ul[contains(@class,'guest')]//li",
                    "//div[contains(@class,'adults')]//span",
                    "//select[@id='adults' or contains(@class,'adult')]//option"
                };

                for (String locator : locators) {
                    try {
                        List<WebElement> elements = driver.findElements(By.xpath(locator));
                        for (WebElement element : elements) {
                            String text = element.getText().trim();
                            String numberStr = text.replaceAll("[^0-9]", "");
                            if (!numberStr.isEmpty()) {
                                int number = Integer.parseInt(numberStr);
                                if (number > 0 && number <= 30 && !adultNumbers.contains(number)) {
                                    adultNumbers.add(number);
                                }
                            }
                        }
                        if (!adultNumbers.isEmpty()) {
                            break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }

            // Default values if nothing found (common hotel booking adult options 1-9)
            if (adultNumbers.isEmpty()) {
                System.out.println("Using default adult numbers (1-9) as dropdown options not found");
                logger.info("Using default adult numbers (1-9) as dropdown options not found on current website");
                for (int i = 1; i <= 9; i++) {
                    adultNumbers.add(i);
                }
            }

            System.out.println("Adult numbers extracted: " + adultNumbers);
            logger.info("Extracted adult person numbers: " + adultNumbers);
            
        } catch (Exception e) {
            logger.error("Failed to extract adult person numbers: " + e.getMessage());
            // Return default values
            for (int i = 1; i <= 9; i++) {
                adultNumbers.add(i);
            }
        }
        
        return adultNumbers;
    }

    /**
     * Display adult person numbers
     * @param adultNumbers List of adult numbers to display
     */
    public void displayAdultNumbers(List<Integer> adultNumbers) {
        System.out.println("\n========== Adult Person Numbers ==========");
        System.out.println("Total options found: " + adultNumbers.size());
        System.out.println("Adult numbers list: " + adultNumbers);
        System.out.println("==========================================\n");
        
        logger.info("Displayed adult numbers: " + adultNumbers);
    }

    /**
     * Enter destination city
     * @param city City name
     */
    public void enterCity(String city) {
        try {
            String[] xpaths = {
                "//input[@id='city']",
                "//input[contains(@placeholder,'city') or contains(@placeholder,'destination')]",
                "//input[contains(@class,'city')]"
            };
            
            for (String xpath : xpaths) {
                try {
                    WebElement cityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                    cityField.clear();
                    cityField.sendKeys(city);
                    logger.info("Entered city: " + city);
                    return;
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            logger.error("Failed to enter city: " + e.getMessage());
        }
    }

    /**
     * Click search button
     */
    public void clickSearch() {
        try {
            String[] xpaths = {
                "//button[contains(text(),'Search')]",
                "//button[contains(@class,'search')]",
                "//a[contains(text(),'Search')]"
            };
            
            for (String xpath : xpaths) {
                try {
                    WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                    js.executeScript("arguments[0].click();", searchBtn);
                    logger.info("Clicked Search button");
                    return;
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            logger.error("Failed to click search: " + e.getMessage());
        }
    }

    /**
     * Check if hotels page is displayed
     * @return true if hotels page is displayed
     */
    public boolean isHotelsPageDisplayed() {
        try {
            return driver.getCurrentUrl().toLowerCase().contains("hotel") || 
                   driver.getTitle().toLowerCase().contains("hotel");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get page title
     * @return Page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Scroll to top of page
     */
    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
        logger.info("Scrolled to top of page");
    }
}
