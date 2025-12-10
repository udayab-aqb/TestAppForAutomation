package com.test.org.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.test.org.utils.WaitHelper;

import java.time.Duration;

/**
 * Base Page class containing common methods for all Page Objects
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WaitHelper waitHelper;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.waitHelper = new WaitHelper(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Get current page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Get current page URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Navigate to a specific URL
     */
    public void navigateTo(String url) {
        driver.get(url);
    }

    /**
     * Refresh the current page
     */
    public void refreshPage() {
        driver.navigate().refresh();
    }
}
