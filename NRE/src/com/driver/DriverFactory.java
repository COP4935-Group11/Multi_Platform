package com.driver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.configuration.RunConfiguration;
import com.configuration.RunConfiguration.OSType;
import com.constants.StringConstants;

public class DriverFactory {

	 private static final Map<DriverType, Supplier<WebDriver>> driverMap = new HashMap<>();

	    //chrome driver supplier
	    private static final Supplier<WebDriver> chromeDriverSupplier = () -> {

	    	if(RunConfiguration.getPlatform().equals(OSType.WINDOWS))	
	    		System.setProperty("webdriver.chrome.driver", StringConstants.ROOT_DIR+"drivers"+StringConstants.ID_SEPARATOR+
	    							"windows"+StringConstants.ID_SEPARATOR+"chromedriver.exe");

	    	
	    	ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-web-security","--allow-running-insecure-content","--ignore-certificate-errors");
	        
	    	return new ChromeDriver(options);
	    };

	    
	    //firefox driver supplier
	    private static final Supplier<WebDriver> firefoxDriverSupplier = () -> {
	        
	    	if(RunConfiguration.getPlatform().equals(OSType.WINDOWS))	
	    		System.setProperty("webdriver.chrome.driver", StringConstants.ROOT_DIR+"drivers"+StringConstants.ID_SEPARATOR+
						"windows"+StringConstants.ID_SEPARATOR+"geckodriver.exe");
	    	
	    	FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--disable-web-security","--allow-running-insecure-content","--ignore-certificate-errors");
	    	
	    	
	        return new FirefoxDriver(options);
	    };
	    
	    //chrome_headless driver supplier
	    private static final Supplier<WebDriver> chromeHeadlessDriverSupplier = () -> {
	        
	    	if(RunConfiguration.getPlatform().equals(OSType.WINDOWS))	
	    		System.setProperty("webdriver.chrome.driver", "./drivers/windows/chromedriver.exe");

	    	
	    	ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-web-security","--allow-running-insecure-content","--ignore-certificate-errors");
	        options.addArguments("--headless");
			
	    	return new ChromeDriver(options);
	    };

	    //firefox_headless driver supplier
	    private static final Supplier<WebDriver> firefoxHeadlessDriverSupplier = () -> {
	    	
	    	if(RunConfiguration.getPlatform().equals(OSType.WINDOWS))	
	    		System.setProperty("webdriver.gecko.driver", "./drivers/windows/geckodriver.exe");
	    	
	    	FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--disable-web-security","--allow-running-insecure-content","--ignore-certificate-errors");
	    	options.addArguments("--headless");
	    	
	        return new FirefoxDriver(options);
	    };

	    //add more suppliers here

	    //add all the drivers into a map
	    static{
	        driverMap.put(DriverType.CHROME, chromeDriverSupplier);
	        driverMap.put(DriverType.FIREFOX, firefoxDriverSupplier);
	        driverMap.put(DriverType.CHROME_HEADLESS, chromeHeadlessDriverSupplier);
	        driverMap.put(DriverType.FIREFOX_HEADLESS, firefoxHeadlessDriverSupplier);
	    }

	    //return a new driver from the map
	    public static final WebDriver getDriver(String browser){
	        
	    	DriverType type = getDriverType(browser);
	    	
	    	return driverMap.get(type).get();
	    }
	    
	    protected static DriverType getDriverType(String browser) {
	    	
	    	switch(browser.toLowerCase()) {
	    	
	    		case "chrome":
	    			return DriverType.CHROME;
	    		case "chrome(headless)":
	    			return DriverType.CHROME_HEADLESS;
	    		case "firefox":
	    			return DriverType.FIREFOX;
	    		case "firefox(headless)":
	    			return DriverType.FIREFOX_HEADLESS;
	    		default:
	    			return DriverType.FIREFOX;    		    	
	    	}
	    		    	
	    }		
		
}
