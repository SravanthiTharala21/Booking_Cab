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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        logger.info("CabsPage initialized");
    }

    public void enterFromCity(String city) {
       
    }

    public void enterToCity(String city) {
       

    private void waitForAutosuggest() {
       
    }

    private void selectAutosuggest(String targetText) {
        String[] keywords = targetText.toLowerCase().split("\\s+");
        
    }

    public void selectDate() {
      
    }

    public void clickSearch() {
       
    }

    private void handleNewWindowIfOpened() {
       
    }

    public void selectSUVCarType() {
       
    }

    public String getLowestCabPrice() {
        
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
       
    }

    public boolean isCabsPageDisplayed() {
       
    }
}