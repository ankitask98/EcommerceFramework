package com.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    Properties properties;

    public ConfigReader() {

        properties = new Properties();

        try {

        	FileInputStream file = new FileInputStream(
        	        "src/test/resources/config.properties");

            properties.load(file);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public String getUrl() {
        return properties.getProperty("url");
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

    public String getBrowser() {
        return properties.getProperty("browser");
    }
}