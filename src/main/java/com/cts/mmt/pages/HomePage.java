package com.cts.mmt.pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cts.mmt.utils.ConfigReader;

/**
 * HomePage - Page Object for MakeMyTrip Home Page
 * Contains all elements and actions for the main landing page
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class HomePage {

    private static final Logger logger = LogManager.getLogger(HomePage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private Actions actions;

    // ==================== Locators ====================
    
    // Popup close button
    @FindBy(css = "span.commonModal__close")
    private WebElement closePopupButton;

    // Navigation tabs
    @FindBy(xpath = "//span[contains(@class,'chNavIcon')]/parent::a[contains(@href,'flights')]")
    private WebElement flightsTab;

    @FindBy(xpath = "//li[contains(@class,'menu_Flights')]//a | //span[text()='Flights']/parent::a")
    private WebElement flightsTabAlt;

    @FindBy(xpath = "//span[text()='Hotels']/parent::a | //li[contains(@class,'menu_Hotels')]//a")
    private WebElement hotelsTab;

    @FindBy(xpath = "//span[text()='Homestays & Villas']/parent::a")
    private WebElement homestaysTab;

    @FindBy(xpath = "//span[text()='Holiday Packages']/parent::a")
    private WebElement holidayPackagesTab;

    @FindBy(xpath = "//span[text()='Trains']/parent::a")
    private WebElement trainsTab;

    @FindBy(xpath = "//span[text()='Buses']/parent::a")
    private WebElement busesTab;

    @FindBy(xpath = "//span[text()='Cabs']/parent::a | //li[contains(@class,'menu_Cabs')]//a")
    private WebElement cabsTab;

    @FindBy(xpath = "//span[text()='Visa']/parent::a")
    private WebElement visaTab;

    // More menu (for Gift Cards, etc.)
    @FindBy(xpath = "//span[text()='More']/parent::span | //li[contains(@class,'more')]//span")
    private WebElement moreMenu;

    @FindBy(xpath = "//a[contains(text(),'Gift Cards') or contains(@href,'giftcard')]")
    private WebElement giftCardsOption;

    // Page title verification
    @FindBy(xpath = "//title")
    private WebElement pageTitle;

    // Header logo
    @FindBy(xpath = "//a[contains(@class,'makemytrip-logo') or contains(@class,'logo')]")
    private WebElement mmtLogo;

    /**
     * Constructor - Initialize page elements using PageFactory
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        
    }

    /**
     * Close the popup if it appears on page load
     */
    public void closePopupIfPresent() {
        
    }

    /**
     * Check if home page is displayed
     * @return true if home page is displayed
     */
    public boolean isHomePageDisplayed() {
       
    }

    /**
     * Click on Cabs tab
     * @return CabsPage instance
     */
    public CabsPage clickCabsTab() {
       
    }

    /**
     * Click on Hotels tab
     * @return HotelsPage instance
     */
    public HotelsPage clickHotelsTab() {
       
    }
    /**
     * Navigate to Gift Cards through More menu using exact locators from Main.java
     * @return GiftCardsPage instance
     */
    public GiftCardsPage navigateToGiftCards() {
        
    }

    /**
     * Click on MakeMyTrip logo to go to home page
     */
    public void clickLogo() {
        
    }

    /**
     * Scroll to top of the page
     */
    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
        logger.info("Scrolled to top of page");
    }

    /**
     * Get page title
     * @return Page title string
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Wait for page to load completely
     */
    public void waitForPageLoad() {
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        logger.info("Page loaded completely");
    }
}
