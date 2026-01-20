package com.cts.mmt.pages;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CabsPage {

    private static final Logger logger = LogManager.getLogger(CabsPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait;
    private JavascriptExecutor js;

    public CabsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        logger.info("CabsPage initialized");
    }

    public void enterFromCity(String city) {
        try {
            System.out.println("Entering From city: " + city);
            WebElement fromWrapper = wait.until(ExpectedConditions.elementToBeClickable(By.id("fromCity")));
            fromWrapper.click();
            WebElement fromInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='From' or contains(@placeholder,'From')]")));
            fromInput.clear();
            fromInput.sendKeys(city);
            selectAutosuggest(city.toLowerCase());
            System.out.println("From city selected: " + city);
            logger.info("From city selection completed: " + city);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to enter From city - " + e.getMessage());
            logger.error("Failed to enter From city: " + e.getMessage());
            throw new RuntimeException("Failed to enter From city", e);
        }
    }

    public void enterToCity(String city) {
        try {
            System.out.println("Entering To city: " + city);
            WebElement toWrapper = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id='top-banner']/div[2]/div/div/div[2]/div[1]/div[2]/div[1]/div/div/div/input")));
            toWrapper.click();
            WebElement toInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='To' or contains(@placeholder,'To')]")));
            toInput.clear();
            toInput.sendKeys(city);
            System.out.println("To city entered: " + city);
            logger.info("To city entered: " + city);
            waitForAutosuggest();
            selectAutosuggest(city.toLowerCase());
            logger.info("To city selection completed: " + city);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to enter To city - " + e.getMessage());
            logger.error("Failed to enter To city: " + e.getMessage());
            throw new RuntimeException("Failed to enter To city", e);
        }
    }

    private void waitForAutosuggest() {
        try {
            shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//ul[@role='listbox']/li | //li[contains(@class,'font')] | //li//p")));
        } catch (Exception e) { }
    }

    private void selectAutosuggest(String targetText) {
        String[] keywords = targetText.toLowerCase().split("\\s+");
        By[] candidateLocators = new By[]{
            By.xpath("//ul[@role='listbox']/li"),
            By.xpath("//li[contains(@class,'font')]"),
            By.xpath("//li//p")
        };
        for (By locator : candidateLocators) {
            try {
                List<WebElement> items = shortWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
                if (items == null || items.isEmpty()) continue;
                WebElement best = null;
                for (WebElement item : items) {
                    try {
                        String txt = item.getText().trim().toLowerCase();
                        if (txt.isEmpty()) continue;
                        boolean allMatch = true;
                        for (String k : keywords) {
                            if (!txt.contains(k)) { allMatch = false; break; }
                        }
                        if (allMatch) { best = item; break; }
                    } catch (Exception ex) { continue; }
                }
                if (best == null) {
                    for (WebElement item : items) {
                        try {
                            String txt = item.getText().toLowerCase();
                            if (txt.contains(keywords[0])) { best = item; break; }
                        } catch (Exception ex) { continue; }
                    }
                }
                if (best != null) {
                    js.executeScript("arguments[0].click();", best);
                    logger.info("Selected autosuggest option");
                    waitForPageLoad();
                    return;
                }
            } catch (Exception e) { continue; }
        }
        try {
            List<WebElement> items = driver.findElements(By.xpath("//ul[@role='listbox']/li"));
            if (!items.isEmpty()) { items.get(0).click(); return; }
        } catch (Exception e) { }
        logger.warn("Could not find autosuggest option for: " + targetText);
    }

    public void selectDate() {
        try {
            System.out.println("Selecting pickup date...");
            // Try clicking on date section - multiple possible locators
            String[] dateLocators = {
                "//div[contains(@class,'pickUpDetails')]//span[contains(@class,'dateInnerWrap')]",
                "//span[contains(@class,'dateInnerWrap')]",
                "//div[contains(@class,'dateContainer')]",
                "//div[contains(text(),'Pick Up')]//following-sibling::div",
                "//p[contains(text(),'PICKUP')]//ancestor::div[contains(@class,'append')]"
            };
            
            boolean dateClicked = false;
            for (String loc : dateLocators) {
                try {
                    WebElement dateField = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc)));
                    js.executeScript("arguments[0].click();", dateField);
                    dateClicked = true;
                    break;
                } catch (Exception e) { continue; }
            }
            
            if (dateClicked) {
                // Try to select a date
                try {
                    shortWait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[contains(@class,'DayPicker')] | //div[contains(@class,'calendar')]")));
                    WebElement dateToSelect = shortWait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[not(contains(@class,'disabled'))]//p[text()='25' or text()='26' or text()='27']")));
                    js.executeScript("arguments[0].click();", dateToSelect);
                    System.out.println("Date selected");
                    logger.info("Pickup date selected");
                } catch (Exception e) {
                    System.out.println("Using default date");
                    logger.info("Using default date - could not change");
                }
            } else {
                System.out.println("Using default date (date picker not found)");
                logger.info("Using default date");
            }
        } catch (Exception e) {
            System.out.println("Using default date");
            logger.warn("Could not select date: " + e.getMessage());
        }
    }

    public void clickSearch() {
        try {
            System.out.println("Clicking Search button...");
            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id='top-banner']/div[2]/div/div/div[2]/p/a")));
            searchBtn.click();
            System.out.println("Search button clicked");
            logger.info("Clicked Search button");
            waitForPageLoad();
            handleNewWindowIfOpened();
        } catch (Exception e) {
            System.out.println("ERROR: Failed to click search - " + e.getMessage());
            logger.error("Failed to click search: " + e.getMessage());
            throw new RuntimeException("Failed to click search", e);
        }
    }

    private void handleNewWindowIfOpened() {
        try {
            String originalWindow = driver.getWindowHandle();
            java.util.Set<String> allWindows = driver.getWindowHandles();
            if (allWindows.size() > 1) {
                for (String window : allWindows) {
                    if (!window.equals(originalWindow)) {
                        driver.switchTo().window(window);
                        logger.info("Switched to new window");
                        return;
                    }
                }
            }
        } catch (Exception e) { }
    }

    public void selectSUVCarType() {
        try {
            System.out.println("Selecting SUV car type...");
            waitForPageLoad();
            try {
                WebElement suvOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[contains(text(),'SUV') or contains(text(),'suv')]")));
                js.executeScript("arguments[0].click();", suvOption);
                System.out.println("SUV car type selected");
                logger.info("Selected SUV car type");
            } catch (Exception e) {
                System.out.println("Warning: Could not find SUV filter - showing all cabs");
                logger.warn("Could not find SUV option");
            }
        } catch (Exception e) {
            logger.warn("Error selecting SUV: " + e.getMessage());
        }
    }

    public String getLowestCabPrice() {
        try {
            System.out.println("Getting lowest SUV cab price...");
            waitForPageLoad();
            
            // Wait for cab results to load
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class,'listingCard')] | //div[contains(@class,'carCard')] | //div[contains(@class,'cabCard')] | //*[contains(@class,'price')]")));
            
            List<Integer> prices = new ArrayList<>();
            
            // Try multiple price locators
            String[] priceXpaths = {
                "//span[contains(@class,'price') or contains(@class,'Price')]",
                "//p[contains(@class,'price') or contains(@class,'Price')]",
                "//div[contains(@class,'price') or contains(@class,'Price')]//span",
                "//*[contains(text(),'₹')]",
                "//*[contains(text(),'Rs')]"
            };
            
            for (String xpath : priceXpaths) {
                try {
                    List<WebElement> priceElements = driver.findElements(By.xpath(xpath));
                    for (WebElement elem : priceElements) {
                        String text = elem.getText().trim();
                        // Extract number from text like "₹15,000" or "Rs. 15000"
                        String numStr = text.replaceAll("[^0-9]", "");
                        if (!numStr.isEmpty()) {
                            int price = Integer.parseInt(numStr);
                            // Filter reasonable cab prices (between 1000 and 100000)
                            if (price >= 1000 && price <= 100000) {
                                prices.add(price);
                            }
                        }
                    }
                } catch (Exception e) { continue; }
            }
            
            String resultPrice;
            if (!prices.isEmpty()) {
                int minPrice = Collections.min(prices);
                resultPrice = "Rs. " + String.format("%,d", minPrice);
            } else {
                // Try to get page content for debugging
                String pageSource = driver.getPageSource();
                if (pageSource.contains("No cabs") || pageSource.contains("no cabs")) {
                    resultPrice = "No cabs available for this route";
                } else {
                    resultPrice = "Price not displayed on current page";
                }
            }
            
            System.out.println("");
            System.out.println("=================================================================");
            System.out.println("        LOWEST SUV CAB PRICE: " + resultPrice);
            System.out.println("=================================================================");
            System.out.println("");
            logger.info("Lowest SUV cab price: " + resultPrice);
            return resultPrice;
        } catch (Exception e) {
            System.out.println("Could not retrieve price: " + e.getMessage());
            return "Price not available";
        }
    }

    public String bookOutstationCab(String fromCity, String toCity) {
        enterFromCity(fromCity);
        enterToCity(toCity);
        selectDate();
        clickSearch();
        selectSUVCarType();
        return getLowestCabPrice();
    }

    private void waitForPageLoad() {
        try {
            shortWait.until(d -> js.executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) { }
    }

    public boolean isCabsPageDisplayed() {
        try {
            return driver.getCurrentUrl().toLowerCase().contains("cab") || 
                   driver.getTitle().toLowerCase().contains("cab");
        } catch (Exception e) { return false; }
    }
}