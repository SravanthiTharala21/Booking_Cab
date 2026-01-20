package com.cts.mmt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ExtentManager - Utility class to manage Extent Reports
 * Implements Singleton pattern for report management
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class ExtentManager {

    private static final Logger logger = LogManager.getLogger(ExtentManager.class);
    private static ExtentReports extent;
    private static final String REPORT_PATH = "test-output/reports/";

    /**
     * Private constructor to prevent instantiation
     */
    private ExtentManager() {
    }

    /**
     * Get ExtentReports instance (Singleton)
     * Creates new report with timestamp if not already created
     * 
     * @return ExtentReports instance
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            synchronized (ExtentManager.class) {
                if (extent == null) {
                    extent = createInstance();
                }
            }
        }
        return extent;
    }

    /**
     * Create new ExtentReports instance with SparkReporter
     * 
     * @return Configured ExtentReports instance
     */
    private static ExtentReports createInstance() {
        // Generate report filename with timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportFileName = REPORT_PATH + "TestReport_" + timestamp + ".html";

        // Initialize SparkReporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFileName);

        // Configure report appearance
        sparkReporter.config().setDocumentTitle("MakeMyTrip Automation Report");
        sparkReporter.config().setReportName("MMT Test Execution Report");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setEncoding("UTF-8");
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        // Initialize ExtentReports and attach reporter
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Set system information
        extent.setSystemInfo("Application", "MakeMyTrip");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", ConfigReader.getInstance().getBrowser());

        logger.info("Extent Report initialized at: " + reportFileName);
        return extent;
    }

    /**
     * Flush the report - Must be called at the end of test execution
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            logger.info("Extent Report flushed successfully");
        }
    }

    /**
     * Get the report path
     * @return Report directory path
     */
    public static String getReportPath() {
        return REPORT_PATH;
    }
}
