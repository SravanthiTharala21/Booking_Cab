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
        logger.info("========== Test Suite Started ==========");
        extent = ExtentManager.getInstance();
        logger.info("Extent Reports initialized");
        
        // Initialize WebDriver once for entire suite
        config = ConfigReader.getInstance();
        String browser = config.getBrowser();
        
        logger.info("Initializing single WebDriver for browser: " + browser);
        initializeDriver(browser);
        
        // Configure timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().window().maximize();
        
        logger.info("WebDriver initialized successfully - Single browser window for all tests");
    }

    /**
     * Method setup - Navigate to base URL before each test method
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        if (config == null) {
            config = ConfigReader.getInstance();
        }
        // Navigate to base URL before each test
        String baseUrl = config.getUrl();
        driver.get(baseUrl);
        logger.info("Navigated to: " + baseUrl);
    }

    /**
     * Initialize WebDriver based on browser type
     * @param browser Browser name (chrome/firefox/edge)
     */
    private void initializeDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                // Basic stability options
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--log-level=3");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("--start-maximized");
                // Chrome 142 stability fixes
                chromeOptions.addArguments("--disable-features=IsolateOrigins,site-per-process");
                chromeOptions.addArguments("--disable-site-isolation-trials");
                chromeOptions.addArguments("--disable-renderer-backgrounding");
                chromeOptions.addArguments("--disable-background-timer-throttling");
                chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
                chromeOptions.addArguments("--disable-hang-monitor");
                chromeOptions.addArguments("--enable-javascript-harmony");
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-logging", "enable-automation"});
                
                if (config.isHeadless()) {
                    chromeOptions.addArguments("--headless");
                }
                
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                
                if (config.isHeadless()) {
                    firefoxOptions.addArguments("--headless");
                }
                
                driver = new FirefoxDriver(firefoxOptions);
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                
                if (config.isHeadless()) {
                    edgeOptions.addArguments("--headless");
                }
                
                driver = new EdgeDriver(edgeOptions);
                break;
                
            default:
                logger.error("Unsupported browser: " + browser + ". Defaulting to Chrome.");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
    }

    /**
     * Method teardown - Quit WebDriver and update report after each test
     * @param result TestNG test result
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        ExtentTest extentTest = test.get();
        
        if (result.getStatus() == ITestResult.FAILURE) {
            // Test failed - capture screenshot and log failure
            logger.error("Test FAILED: " + result.getName());
            
            if (extentTest != null) {
                extentTest.log(Status.FAIL, "Test FAILED: " + result.getThrowable().getMessage());
                
                // Capture and attach screenshot
                String base64Screenshot = ScreenshotUtil.captureScreenshotAsBase64(getDriver());
                if (base64Screenshot != null) {
                    extentTest.fail("Screenshot on failure",
                            MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
                }
                
                // Also save screenshot to file
                ScreenshotUtil.captureScreenshot(getDriver(), result.getName() + "_FAILED");
            }
            
        } else if (result.getStatus() == ITestResult.SKIP) {
            // Test skipped
            logger.warn("Test SKIPPED: " + result.getName());
            if (extentTest != null) {
                extentTest.log(Status.SKIP, "Test SKIPPED: " + result.getThrowable().getMessage());
            }
            
        } else {
            // Test passed
            logger.info("Test PASSED: " + result.getName());
            if (extentTest != null) {
                extentTest.log(Status.PASS, "Test PASSED");
            }
        }
        
        // Do NOT quit WebDriver here - single browser for all tests
        // Browser will be closed in afterSuite()
        logger.info("Test method completed: " + result.getName());
    }

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
        String url = config.getUrl();
        getDriver().get(url);
        logInfo("Navigated to: " + url);
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
        long endTime = System.currentTimeMillis() + (seconds * 1000L);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds + 1));
        wait.until(d -> System.currentTimeMillis() >= endTime);
    }
}
