package com.cts.mmt.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ConfigReader - Utility class to read configuration properties from config.properties file
 * Implements Singleton pattern to ensure single instance throughout the framework
 * 
 * @author CTS Automation Team
 * @version 1.0
 */
public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static ConfigReader configReader;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    /**
     * Private constructor to prevent instantiation
     * Loads properties from config file
     */
    private ConfigReader() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            logger.info("Configuration file loaded successfully from: " + CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: " + e.getMessage());
            throw new RuntimeException("Could not load config.properties file", e);
        }
    }

    /**
     * Get singleton instance of ConfigReader
     * @return ConfigReader instance
     */
    public static ConfigReader getInstance() {
        if (configReader == null) {
            synchronized (ConfigReader.class) {
                if (configReader == null) {
                    configReader = new ConfigReader();
                }
            }
        }
        return configReader;
    }

    /**
     * Get property value by key
     * @param key Property key
     * @return Property value
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property not found for key: " + key);
        }
        return value;
    }

    /**
     * Get browser type from configuration
     * @return Browser name (chrome/firefox/edge)
     */
    public String getBrowser() {
        return getProperty("browser");
    }

    /**
     * Get application URL from configuration
     * @return Application URL
     */
    public String getUrl() {
        return getProperty("url");
    }

    /**
     * Get username from configuration
     * @return Username
     */
    public String getUsername() {
        return getProperty("username");
    }

    /**
     * Get password from configuration
     * @return Password
     */
    public String getPassword() {
        return getProperty("password");
    }

    /**
     * Get implicit wait timeout
     * @return Implicit wait timeout in seconds
     */
    public int getImplicitWait() {
        String wait = getProperty("implicit.wait");
        return wait != null ? Integer.parseInt(wait) : 10;
    }

    /**
     * Get explicit wait timeout
     * @return Explicit wait timeout in seconds
     */
    public int getExplicitWait() {
        String wait = getProperty("explicit.wait");
        return wait != null ? Integer.parseInt(wait) : 20;
    }

    /**
     * Get page load timeout
     * @return Page load timeout in seconds
     */
    public int getPageLoadTimeout() {
        String timeout = getProperty("page.load.timeout");
        return timeout != null ? Integer.parseInt(timeout) : 30;
    }

    /**
     * Check if headless mode is enabled
     * @return true if headless mode is enabled
     */
    public boolean isHeadless() {
        String headless = getProperty("headless");
        return headless != null && Boolean.parseBoolean(headless);
    }
}
