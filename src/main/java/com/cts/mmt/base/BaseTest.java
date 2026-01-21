package com.cts.mmt.base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.cts.mmt.utils.ConfigReader;
import com.cts.mmt.utils.ExtentManager;
import com.cts.mmt.utils.ScreenshotUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * BaseTest - Base class for all test classes
 * Handles WebDriver setup/teardown and Extent Reports integration
 * Uses single browser window for entire test suite
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    
    // Static WebDriver - single instance for all tests
    protected static WebDriver driver;
    
    // Extent Reports instances
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    
    // Configuration reader
    protected ConfigReader config;

    /**
     * Suite setup - Initialize Extent Reports and WebDriver (single browser for all tests)
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        
    }

    /**
     * Method setup - Do NOT navigate, just initialize config if needed
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        
    }

    /**
     * Initialize WebDriver based on browser type
     * @param browser Browser name (chrome/firefox/edge)
     */
    private void initializeDriver(String browser) {
        
    }

    /**
     * Method teardown - Quit WebDriver and update report after each test
     * @param result TestNG test result
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        ExtentTest extentTest = test.get();
    
    /**
     * Suite teardown - Flush Extent Reports and close browser
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        // Close browser at the end of entire suite
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed - Browser session ended");
        }
        ExtentManager.flushReport();
        logger.info("========== Test Suite Completed ==========");
    }

    /**
     * Get WebDriver instance
     * @return WebDriver instance
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Get ExtentTest instance
     * @return ExtentTest instance for current thread
     */
    public ExtentTest getTest() {
        return test.get();
    }

    /**
     * Create new ExtentTest for a test method
     * @param testName Test name
     * @param description Test description
     * @return ExtentTest instance
     */
    protected ExtentTest createTest(String testName, String description) {
        ExtentTest extentTest = extent.createTest(testName, description);
        test.set(extentTest);
        return extentTest;
    }

    /**
     * Log info message to Extent Report
     * @param message Message to log
     */
    protected void logInfo(String message) {
        logger.info(message);
        if (test.get() != null) {
            test.get().log(Status.INFO, message);
        }
    }

    /**
     * Log pass message to Extent Report
     * @param message Message to log
     */
    protected void logPass(String message) {
        logger.info("PASS: " + message);
        if (test.get() != null) {
            test.get().log(Status.PASS, message);
        }
    }

    /**
     * Log fail message to Extent Report
     * @param message Message to log
     */
    protected void logFail(String message) {
        logger.error("FAIL: " + message);
        if (test.get() != null) {
            test.get().log(Status.FAIL, message);
        }
    }

    /**
     * Navigate to application URL
     */
    protected void navigateToApplication() {
       
    }

    /**
     * Wait for page to load completely using explicit wait
     * Uses document.readyState to determine if page is fully loaded
     */
    protected void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(config.getExplicitWait()));
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Wait for a specific duration using FluentWait
     * This allows user to see the results on screen before browser closes
     * @param seconds Number of seconds to wait
     */
    protected void waitForSeconds(int seconds) {
      
    }
}
