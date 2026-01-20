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
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInstance().getExplicitWait()));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
        logger.info("HomePage initialized");
    }

    /**
     * Close the popup if it appears on page load
     */
    public void closePopupIfPresent() {
        try {
            WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(closePopupButton));
            js.executeScript("arguments[0].click();", popup);
            logger.info("Popup closed successfully");
        } catch (Exception e) {
            logger.info("No popup appeared or popup already closed");
        }
    }

    /**
     * Check if home page is displayed
     * @return true if home page is displayed
     */
    public boolean isHomePageDisplayed() {
        try {
            wait.until(ExpectedConditions.titleContains("MakeMyTrip"));
            String title = driver.getTitle();
            logger.info("Page title: " + title);
            return title.toLowerCase().contains("makemytrip");
        } catch (Exception e) {
            logger.error("Home page verification failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click on Cabs tab
     * @return CabsPage instance
     */
    public CabsPage clickCabsTab() {
        try {
            // Try direct XPath first
            WebElement cabTab = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"header-container\"]/div[2]/div/div/nav/ul/li[7]/span/a/span[1]/span")));
            cabTab.click();
        } catch (Exception e) {
            // Fallback to generic locator
            try {
                WebElement cabTab = wait.until(ExpectedConditions.elementToBeClickable(cabsTab));
                js.executeScript("arguments[0].click();", cabTab);
            } catch (Exception e2) {
                // Another fallback
                WebElement cabTab = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[contains(text(),'Cabs')]")));
                js.executeScript("arguments[0].click();", cabTab);
            }
        }
        logger.info("Clicked on Cabs tab");
        return new CabsPage(driver);
    }

    /**
     * Click on Hotels tab
     * @return HotelsPage instance
     */
    public HotelsPage clickHotelsTab() {
        try {
            // Scroll to top first
            js.executeScript("window.scrollTo(0, 0);");
            WebElement hotelElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"header-container\"]/div/div/div/nav/ul/li[2]/span/a/span[1]/span")));
            wait.until(ExpectedConditions.elementToBeClickable(hotelElement));
            js.executeScript("arguments[0].click();", hotelElement);
        } catch (Exception e) {
            // Fallback
            WebElement hotelElement = wait.until(ExpectedConditions.elementToBeClickable(hotelsTab));
            js.executeScript("arguments[0].click();", hotelElement);
        }
        logger.info("Clicked on Hotels tab");
        return new HotelsPage(driver);
    }

    /**
     * Navigate to Gift Cards through More menu using exact locators from Main.java
     * @return GiftCardsPage instance
     */
    public GiftCardsPage navigateToGiftCards() {
        try {
            // Step 1: Locate the element to hover (More menu)
            WebElement hoverElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("/html/body/div[1]/div/div/div/nav/ul/li[11]/span/span[1]")));
            // Step 2: Perform mouse hover
            actions.moveToElement(hoverElement).perform();
            // Step 3: After hover, wait for the dropdown option to appear
            WebElement optionToClick = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[1]/div/div/div/nav/ul/li[11]/div/a[2]")));
            // Step 4: Click the option
            optionToClick.click();
            logger.info("Navigated to Gift Cards page through menu");
            
            // Wait for page to load
            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
            
        } catch (Exception e) {
            // Fallback: Direct navigation to Gift Cards URL
            driver.get("https://www.makemytrip.com/gift-cards/");
            logger.info("Navigated to Gift Cards via direct URL");
            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        }
        return new GiftCardsPage(driver);
    }

    /**
     * Click on MakeMyTrip logo to go to home page
     */
    public void clickLogo() {
        try {
            js.executeScript("window.scrollTo(0, 0);");
            WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(mmtLogo));
            js.executeScript("arguments[0].click();", logo);
            logger.info("Clicked on MMT logo");
        } catch (Exception e) {
            driver.navigate().to(ConfigReader.getInstance().getUrl());
            logger.info("Navigated to home page via URL");
        }
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
