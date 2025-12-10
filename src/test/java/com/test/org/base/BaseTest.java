package com.test.org.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import com.test.org.utils.ConfigReader;
import java.io.File;

/**
 * Base Test class with ThreadLocal WebDriver for parallel test execution
 * Uses WebDriverManager for automatic driver binary management
 * Automatically loads TestAppForAutomation.html before each test
 */
public class BaseTest {

    // ThreadLocal WebDriver for parallel execution support
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected ConfigReader configReader;

    /**
     * Get WebDriver instance for current thread
     * 
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Set WebDriver instance for current thread
     * 
     * @param webDriver WebDriver instance to set
     */
    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    /**
     * Setup method executed before each test method
     * Initializes WebDriver and automatically loads the test HTML file
     * 
     * @param browser Browser type (chrome, firefox, edge) - optional parameter from
     *                testng.xml
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional String browser) {
        configReader = new ConfigReader();

        // Use parameter if provided, otherwise use config
        String browserType = (browser != null && !browser.isEmpty())
                ? browser
                : configReader.getProperty("browser", "chrome");

        // Initialize WebDriver based on browser type
        WebDriver webDriver = initializeDriver(browserType);
        setDriver(webDriver);

        // Configure WebDriver
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();

        // Load the HTML file automatically
        loadTestApplication();
    }

    /**
     * Load the TestAppForAutomation.html file
     * First tries to use baseUrl from config, then falls back to default path
     */
    private void loadTestApplication() {
        String baseUrl = configReader.getProperty("baseUrl");

        if (baseUrl != null && !baseUrl.isEmpty()) {
            // Use baseUrl from config.properties
            getDriver().get(baseUrl);
        } else {
            // Fallback: construct path to HTML file
            String htmlFilePath = "src/test/resources/app/TestAppForAutomation.html";
            File htmlFile = new File(htmlFilePath);

            if (htmlFile.exists()) {
                String fileUrl = htmlFile.toURI().toString();
                getDriver().get(fileUrl);
                System.out.println("Loaded HTML file from: " + fileUrl);
            } else {
                System.err.println("ERROR: TestAppForAutomation.html not found at: " + htmlFile.getAbsolutePath());
                System.err.println("Please ensure the HTML file exists or set 'baseUrl' in config.properties");
            }
        }
    }

    /**
     * Initialize WebDriver using WebDriverManager
     * 
     * @param browser Browser type (chrome, firefox, edge)
     * @return WebDriver instance
     */
    private WebDriver initializeDriver(String browser) {
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                break;

            default:
                System.out.println("Browser '" + browser + "' not supported. Using Chrome by default.");
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
        }

        return webDriver;
    }

    /**
     * Teardown method executed after each test method
     * Quits WebDriver and removes it from ThreadLocal
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove(); // Remove ThreadLocal variable
        }
    }
}
