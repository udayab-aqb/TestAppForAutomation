package com.test.org.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.test.org.base.BaseTest;
import com.test.org.pages.TestAppPage;
import io.qameta.allure.*;

/**
 * Test class for TestAppForAutomation.html
 * Contains comprehensive tests for login, dropdown, and validation scenarios
 */
@Epic("Test App Automation")
@Feature("TestAppForAutomation.html Testing")
public class TestAppTest extends BaseTest {

    private TestAppPage testAppPage;

    @BeforeMethod
    public void initializePage() {
        testAppPage = new TestAppPage(getDriver());
    }

    // ==================== Login Tests ====================

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    @Story("Login Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test that user can successfully login with valid email and password")
    public void testSuccessfulLogin() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";

        // Act
        testAppPage.performLogin(email, password);

        // Assert
        Assert.assertTrue(testAppPage.isLoginSuccessful(),
                "Login should be successful with valid credentials");
        Assert.assertEquals(testAppPage.getStatusMessage(), "Login successful",
                "Status message should indicate successful login");
    }

    @Test(priority = 2, description = "Verify login fails with missing email")
    @Story("Login Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test that login fails when email is not provided")
    public void testLoginWithMissingEmail() {
        // Arrange & Act
        testAppPage.setPassword("password123");
        testAppPage.clickLogin();

        // Assert
        Assert.assertTrue(testAppPage.isMissingCredentials(),
                "Login should fail with missing email");
        Assert.assertEquals(testAppPage.getStatusMessage(), "Missing credentials",
                "Status message should indicate missing credentials");
    }

    @Test(priority = 3, description = "Verify login fails with missing password")
    @Story("Login Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test that login fails when password is not provided")
    public void testLoginWithMissingPassword() {
        // Arrange & Act
        testAppPage.setEmail("test@example.com");
        testAppPage.clickLogin();

        // Assert
        Assert.assertTrue(testAppPage.isMissingCredentials(),
                "Login should fail with missing password");
        Assert.assertEquals(testAppPage.getStatusMessage(), "Missing credentials",
                "Status message should indicate missing credentials");
    }

    @Test(priority = 4, description = "Verify login fails with both email and password missing")
    @Story("Login Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test that login fails when both email and password are not provided")
    public void testLoginWithMissingCredentials() {
        // Act
        testAppPage.clickLogin();

        // Assert
        Assert.assertTrue(testAppPage.isMissingCredentials(),
                "Login should fail with missing credentials");
        Assert.assertEquals(testAppPage.getStatusMessage(), "Missing credentials",
                "Status message should indicate missing credentials");
    }

    // ==================== Dropdown Tests ====================

    @Test(priority = 5, description = "Verify dropdown selection and submission with Option 1")
    @Story("Dropdown Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test that user can select Option 1 from dropdown and submit")
    public void testDropdownSelectOption1() {
        // Act
        testAppPage.selectAndSubmit("opt1");

        // Assert
        Assert.assertTrue(testAppPage.statusMessageContains("Submitted: opt1"),
                "Status message should show opt1 was submitted");
    }

    @Test(priority = 6, description = "Verify dropdown selection and submission with Option 2")
    @Story("Dropdown Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test that user can select Option 2 from dropdown and submit")
    public void testDropdownSelectOption2() {
        // Act
        testAppPage.selectAndSubmit("opt2");

        // Assert
        Assert.assertTrue(testAppPage.statusMessageContains("Submitted: opt2"),
                "Status message should show opt2 was submitted");
    }

    @Test(priority = 7, description = "Verify dropdown submission without selection")
    @Story("Dropdown Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test that submission without dropdown selection shows appropriate message")
    public void testDropdownSubmitWithoutSelection() {
        // Act
        testAppPage.clickSubmit();

        // Assert
        Assert.assertEquals(testAppPage.getStatusMessage(), "Select an option",
                "Status message should prompt to select an option");
    }

    @Test(priority = 8, description = "Verify dropdown selection by visible text")
    @Story("Dropdown Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test selecting dropdown option using visible text")
    public void testDropdownSelectByText() {
        // Act
        testAppPage.selectDropdownByText("Option 1");

        // Assert
        Assert.assertEquals(testAppPage.getSelectedDropdownOption(), "Option 1",
                "Selected option should be 'Option 1'");
    }

    // ==================== Status Message Tests ====================

    @Test(priority = 9, description = "Verify status message is displayed after login")
    @Story("Status Message Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test that status message is visible after performing login action")
    public void testStatusMessageDisplayedAfterLogin() {
        // Act
        testAppPage.performLogin("test@example.com", "pass123");

        // Assert
        Assert.assertTrue(testAppPage.isStatusMessageDisplayed(),
                "Status message should be displayed after login");
    }

    @Test(priority = 10, description = "Verify status message is displayed after dropdown submit")
    @Story("Status Message Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test that status message is visible after dropdown submission")
    public void testStatusMessageDisplayedAfterSubmit() {
        // Act
        testAppPage.clickSubmit();

        // Assert
        Assert.assertTrue(testAppPage.isStatusMessageDisplayed(),
                "Status message should be displayed after submit");
    }

    // ==================== Integration Tests ====================

    @Test(priority = 11, description = "Verify sequential login and dropdown operations")
    @Story("Integration Testing")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test that user can perform login followed by dropdown selection")
    public void testLoginThenDropdownSubmit() {
        // Act - Login first
        testAppPage.performLogin("user@test.com", "password");
        Assert.assertTrue(testAppPage.isLoginSuccessful(), "Login should be successful");

        // Act - Then use dropdown
        testAppPage.selectAndSubmit("opt1");

        // Assert
        Assert.assertTrue(testAppPage.statusMessageContains("Submitted: opt1"),
                "Status should show dropdown submission after login");
    }

    @Test(priority = 12, description = "Verify page title")
    @Story("Page Validation")
    @Severity(SeverityLevel.MINOR)
    @Description("Test that page title is correctly set")
    public void testPageTitle() {
        // Assert
        Assert.assertEquals(testAppPage.getPageTitle(), "Test App For Automation",
                "Page title should match expected value");
    }
}
