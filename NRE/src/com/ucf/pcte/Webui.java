package com.ucf.pcte;

import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.DatatypeConverter;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




public class Webui {
	private WebDriver driver;
	static Webui open;
	

	
	public static void openBrowser(String nothing)
	{
		
		open = new Webui();
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--disable-web-security","--allow-running-insecure-content","--ignore-certificate-errors");
		options.addArguments("--headless");
		open.driver = new FirefoxDriver(options);
	    open.driver.manage().window().maximize();
	    open.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public static void navigateToUrl(String url)
	{
		open.driver.navigate().to(url);
	}
	
	public static void setText(String xpath, String text)
	{
		WebElement element = open.driver.findElement(By.xpath(xpath));
		element.sendKeys(text);
		
	}
	
	public static void setEncryptedText(String xpath, String text)
	{
		WebElement element = open.driver.findElement(By.xpath(xpath));
		String rawText = "";
		
		try 
		{
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEwithSHA1AndDESede");
	        SecretKey key = keyFactory.generateSecret(new PBEKeySpec("S3cReT K3i".toCharArray()));
	        Cipher pbeCipher = Cipher.getInstance("PBEwithSHA1AndDESede");
	        pbeCipher.init(2, key, new PBEParameterSpec("K@tal0n STudlO".getBytes(), 20));
	        
			rawText = new String(pbeCipher.doFinal(DatatypeConverter.parseBase64Binary(text)), "UTF-8");
		
		}catch (Exception e)
		{
			System.out.println("Couldn't decode password.");
		}
		element.sendKeys(rawText);
		
	}
	
	public static void click(String xpath)
	{
		WebElement element = open.driver.findElement(By.xpath(xpath));
		element.click();
		
	}
	
	public static void verifyElementText(String xpath, String text)
	{
		WebElement element = open.driver.findElement(By.xpath(xpath));
		if(!element.getText().equals(text))
		{
			Assert.fail();
		}
		
	}
	
	public static void verifyElementNotPresent(String xpath, int time)
	{
		Boolean notLocated = new WebDriverWait(open.driver, time).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
	    
		if(notLocated == true)
		{
			return;
		}else 
		{
			Assert.fail();
		}
	    
	}
	
	public static void verifyElementPresent(String xpath, int time)
	{
	    try
	    {
	    	open.driver.findElement(By.xpath(xpath));
	    }catch (Exception e)
	    {
	    	Assert.fail();
	    }
		
	}
	public static void closeBrowser()
	{
		open.driver.close();
		open.driver.quit();
	}
}