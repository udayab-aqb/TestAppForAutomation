package com.test.org.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object Model for TestAppForAutomation.html
 */
public class TestAppPage extends BasePage {

    // Locators using @FindBy annotations
    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "loginBtn")
    private WebElement loginButton;

    @FindBy(id = "dropdown")
    private WebElement dropdown;

    @FindBy(id = "submitBtn")
    private WebElement submitButton;

    @FindBy(id = "sampleLink")
    private WebElement sampleLink;

    @FindBy(id = "status")
    private WebElement statusMessage;

    // Constructor
    public TestAppPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Set email in the email field
     * 
     * @param email Email address to enter
     */
    public void setEmail(String email) {
        waitHelper.waitForElementVisible(emailField);
        emailField.clear();
        emailField.sendKeys(email);
    }

    /**
     * Set password in the password field
     * 
     * @param password Password to enter
     */
    public void setPassword(String password) {
        waitHelper.waitForElementVisible(passwordField);
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /**
     * Click the login button
     */
    public void clickLogin() {
        waitHelper.waitForElementClickable(loginButton);
        loginButton.click();
    }

    /**
     * Perform complete login action
     * 
     * @param email    Email address
     * @param password Password
     */
    public void performLogin(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLogin();
    }

    /**
     * Select an option from the dropdown by value
     * 
     * @param optionValue Value of the option to select (e.g., "opt1", "opt2")
     */
    public void selectDropdownOption(String optionValue) {
        waitHelper.waitForElementVisible(dropdown);
        Select select = new Select(dropdown);
        select.selectByValue(optionValue);
    }

    /**
     * Select an option from the dropdown by visible text
     * 
     * @param optionText Visible text of the option to select
     */
    public void selectDropdownByText(String optionText) {
        waitHelper.waitForElementVisible(dropdown);
        Select select = new Select(dropdown);
        select.selectByVisibleText(optionText);
    }

    /**
     * Get the currently selected dropdown option
     * 
     * @return Selected option text
     */
    public String getSelectedDropdownOption() {
        Select select = new Select(dropdown);
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Click the submit button
     */
    public void clickSubmit() {
        waitHelper.waitForElementClickable(submitButton);
        submitButton.click();
    }

    /**
     * Select dropdown option and submit
     * 
     * @param optionValue Value of the option to select
     */
    public void selectAndSubmit(String optionValue) {
        selectDropdownOption(optionValue);
        clickSubmit();
    }

    /**
     * Click the sample navigation link
     */
    public void clickSampleLink() {
        waitHelper.waitForElementClickable(sampleLink);
        sampleLink.click();
    }

    /**
     * Get the status message text
     * 
     * @return Status message text
     */
    public String getStatusMessage() {
        waitHelper.waitForElementVisible(statusMessage);
        return statusMessage.getText();
    }

    /**
     * Check if status message is displayed
     * 
     * @return true if status message is visible, false otherwise
     */
    public boolean isStatusMessageDisplayed() {
        try {
            return statusMessage.isDisplayed() && !statusMessage.getText().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if status message contains specific text
     * 
     * @param expectedText Expected text in status message
     * @return true if status message contains the expected text
     */
    public boolean statusMessageContains(String expectedText) {
        try {
            waitHelper.waitForElementVisible(statusMessage);
            return statusMessage.getText().contains(expectedText);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify if login was successful
     * 
     * @return true if status message indicates successful login
     */
    public boolean isLoginSuccessful() {
        return statusMessageContains("Login successful");
    }

    /**
     * Verify if credentials are missing
     * 
     * @return true if status message indicates missing credentials
     */
    public boolean isMissingCredentials() {
        return statusMessageContains("Missing credentials");
    }
}
