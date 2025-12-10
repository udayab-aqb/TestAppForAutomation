package com.test.org.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Screenshot Utility class for capturing screenshots
 */
public class ScreenshotUtil {

    /**
     * Capture screenshot and save to specified path
     * 
     * @param driver         WebDriver instance
     * @param screenshotName Name of the screenshot file
     * @return Path to the saved screenshot
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = screenshotName + "_" + timestamp + ".png";

        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);

        String screenshotPath = "./screenshots/" + fileName;
        File destination = new File(screenshotPath);

        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File("./screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot saved: " + screenshotPath);
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }

        return screenshotPath;
    }

    /**
     * Capture screenshot with default name (based on current timestamp)
     */
    public static String captureScreenshot(WebDriver driver) {
        return captureScreenshot(driver, "screenshot");
    }
}
