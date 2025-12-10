package com.test.org.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Reader class for reading properties file
 */
public class ConfigReader {
    private Properties properties;

    public ConfigReader() {
        try {
            String configPath = "src/test/resources/config/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties file");
        }
    }

    /**
     * Get property value by key
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
