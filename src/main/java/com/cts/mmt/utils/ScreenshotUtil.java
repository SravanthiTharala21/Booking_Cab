package com.cts.mmt.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * ScreenshotUtil - Utility class for capturing screenshots
 * Used for capturing screenshots on test failures
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_PATH = "test-output/screenshots/";

    /**
     * Capture screenshot and save to file
     * 
     * @param driver WebDriver instance
     * @param screenshotName Name for the screenshot file
     * @return Path to the saved screenshot
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) {
            logger.error("Cannot capture screenshot - WebDriver is null");
            return null;
        }

        // Generate unique filename with timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = screenshotName + "_" + timestamp + ".png";
        String filePath = SCREENSHOT_PATH + fileName;

        try {
            // Create screenshots directory if not exists
            File screenshotDir = new File(SCREENSHOT_PATH);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            // Capture screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);

            // Copy screenshot to destination
            FileUtils.copyFile(source, destination);
            logger.info("Screenshot captured: " + filePath);

            return filePath;
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Capture screenshot and return as Base64 string for Extent Reports
     * 
     * @param driver WebDriver instance
     * @return Base64 encoded screenshot string
     */
    public static String captureScreenshotAsBase64(WebDriver driver) {
        if (driver == null) {
            logger.error("Cannot capture screenshot - WebDriver is null");
            return null;
        }

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            String base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);
            logger.info("Screenshot captured as Base64");
            return base64Screenshot;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot as Base64: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get screenshot directory path
     * @return Screenshot directory path
     */
    public static String getScreenshotPath() {
        return SCREENSHOT_PATH;
    }

    /**
     * Delete all screenshots from the screenshot directory
     * Useful for cleanup before test execution
     */
    public static void cleanScreenshotDirectory() {
        File screenshotDir = new File(SCREENSHOT_PATH);
        if (screenshotDir.exists()) {
            File[] files = screenshotDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".png")) {
                        file.delete();
                    }
                }
            }
            logger.info("Screenshot directory cleaned");
        }
    }
}
