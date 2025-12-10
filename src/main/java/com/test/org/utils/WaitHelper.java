package com.test.org.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * Enhanced Wait Helper class for handling explicit and fluent waits
 * Provides comprehensive wait utilities for robust test automation
 */
public class WaitHelper {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 10;
    private static final int DEFAULT_POLLING = 500;

    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    public WaitHelper(WebDriver driver, int timeoutInSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    // ==================== ELEMENT VISIBILITY WAITS ====================

    /**
     * Wait for element to be visible
     */
    public WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be visible with custom timeout
     */
    public WebElement waitForElementVisible(WebElement element, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element located by locator to be visible
     */
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element located by locator to be visible with custom timeout
     */
    public WebElement waitForElementVisible(By locator, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for all elements to be visible
     */
    public List<WebElement> waitForAllElementsVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ==================== ELEMENT CLICKABILITY WAITS ====================

    /**
     * Wait for element to be clickable
     */
    public WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element to be clickable with custom timeout
     */
    public WebElement waitForElementClickable(WebElement element, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element located by locator to be clickable
     */
    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element located by locator to be clickable with custom timeout
     */
    public WebElement waitForElementClickable(By locator, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ==================== ELEMENT INVISIBILITY WAITS ====================

    /**
     * Wait for element to be invisible
     */
    public boolean waitForElementInvisible(WebElement element) {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Wait for element to be invisible with custom timeout
     */
    public boolean waitForElementInvisible(WebElement element, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Wait for element located by locator to be invisible
     */
    public boolean waitForElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // ==================== ELEMENT PRESENCE WAITS ====================

    /**
     * Wait for presence of element located by locator
     */
    public WebElement waitForPresenceOfElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for presence of element with custom timeout
     */
    public WebElement waitForPresenceOfElement(By locator, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for presence of all elements
     */
    public List<WebElement> waitForPresenceOfAllElements(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // ==================== TEXT WAITS ====================

    /**
     * Wait for text to be present in element
     */
    public boolean waitForTextToBePresentInElement(WebElement element, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    /**
     * Wait for text to be present in element located by locator
     */
    public boolean waitForTextToBePresentInElement(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait for text to be present in element value
     */
    public boolean waitForTextToBePresentInElementValue(WebElement element, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    /**
     * Wait for text to be present in element value located by locator
     */
    public boolean waitForTextToBePresentInElementValue(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementValue(locator, text));
    }

    // ==================== ATTRIBUTE WAITS ====================

    /**
     * Wait for attribute to contain value
     */
    public boolean waitForAttributeContains(By locator, String attribute, String value) {
        return wait.until(ExpectedConditions.attributeContains(locator, attribute, value));
    }

    /**
     * Wait for attribute to be
     */
    public boolean waitForAttributeToBe(By locator, String attribute, String value) {
        return wait.until(ExpectedConditions.attributeToBe(locator, attribute, value));
    }

    // ==================== SELECTION WAITS ====================

    /**
     * Wait for element to be selected
     */
    public boolean waitForElementToBeSelected(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeSelected(element));
    }

    /**
     * Wait for element to be selected by locator
     */
    public boolean waitForElementToBeSelected(By locator) {
        return wait.until(ExpectedConditions.elementToBeSelected(locator));
    }

    /**
     * Wait for element selection state to be
     */
    public boolean waitForElementSelectionStateToBe(WebElement element, boolean selected) {
        return wait.until(ExpectedConditions.elementSelectionStateToBe(element, selected));
    }

    // ==================== ALERT WAITS ====================

    /**
     * Wait for alert to be present
     */
    public void waitForAlertPresent() {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Wait for alert and accept
     */
    public void waitForAlertAndAccept() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    /**
     * Wait for alert and dismiss
     */
    public void waitForAlertAndDismiss() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    // ==================== FRAME WAITS ====================

    /**
     * Wait for frame to be available and switch to it
     */
    public WebDriver waitForFrameAndSwitch(By locator) {
        return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    /**
     * Wait for frame to be available and switch to it by index
     */
    public WebDriver waitForFrameAndSwitch(int frameIndex) {
        return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    // ==================== URL AND TITLE WAITS ====================

    /**
     * Wait for URL to be
     */
    public boolean waitForUrlToBe(String url) {
        return wait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Wait for URL to contain
     */
    public boolean waitForUrlContains(String fraction) {
        return wait.until(ExpectedConditions.urlContains(fraction));
    }

    /**
     * Wait for title to be
     */
    public boolean waitForTitleIs(String title) {
        return wait.until(ExpectedConditions.titleIs(title));
    }

    /**
     * Wait for title to contain
     */
    public boolean waitForTitleContains(String title) {
        return wait.until(ExpectedConditions.titleContains(title));
    }

    // ==================== STALENESS WAITS ====================

    /**
     * Wait for element to become stale
     */
    public boolean waitForStalenessOf(WebElement element) {
        return wait.until(ExpectedConditions.stalenessOf(element));
    }

    // ==================== FLUENT WAIT ====================

    /**
     * Create a custom fluent wait
     */
    public FluentWait<WebDriver> createFluentWait(int timeoutInSeconds, int pollingInMillis) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(pollingInMillis))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * Wait for element using fluent wait
     */
    public WebElement fluentWaitForElement(By locator, int timeoutInSeconds, int pollingInMillis) {
        FluentWait<WebDriver> fluentWait = createFluentWait(timeoutInSeconds, pollingInMillis);
        return fluentWait.until(driver -> driver.findElement(locator));
    }

    // ==================== IMPLICIT WAIT ====================

    /**
     * Set implicit wait timeout
     */
    public void setImplicitWait(int timeoutInSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Set page load timeout
     */
    public void setPageLoadTimeout(int timeoutInSeconds) {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Set script timeout
     */
    public void setScriptTimeout(int timeoutInSeconds) {
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(timeoutInSeconds));
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Get the current WebDriverWait instance
     */
    public WebDriverWait getWait() {
        return wait;
    }

    /**
     * Create a custom WebDriverWait with specific timeout
     */
    public WebDriverWait createCustomWait(int timeoutInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }
}
