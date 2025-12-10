package com.test.org.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * WebDriver Factory class for managing browser instances
 */
public class WebDriverFactory {

    /**
     * Get WebDriver instance based on browser type
     * 
     * @param browser - chrome, firefox, or edge
     * @return WebDriver instance
     */
    public static WebDriver getDriver(String browser) {
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                System.out.println("Browser not supported. Using Chrome by default.");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }

        return driver;
    }
}
