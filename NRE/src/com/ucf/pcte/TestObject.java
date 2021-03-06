package com.ucf.pcte;

import java.io.File;
import javax.xml.parsers.*;

import org.openqa.selenium.WebElement;
import org.w3c.dom.*;

import com.configuration.RunConfiguration;
import com.constants.StringConstants;


public class TestObject {
	
	@SuppressWarnings("unused")
	public static String findTestObject(String location)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(RunConfiguration.getProjectDir() + StringConstants.ID_SEPARATOR 
												+ location + StringConstants.OBJECTS_EXT));
			document.getDocumentElement().normalize();
			Element root = document.getDocumentElement();
			NodeList nList = document.getElementsByTagName("selectorCollection");
			
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
			 Node node = nList.item(temp);
			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {
				Element eElement = (Element) node;
			    return eElement.getElementsByTagName("value").item(0).getTextContent();
			 }
			}
      
			
		}catch (Exception e)
		{
			System.out.println(e);
		}
		return "couldn't find";
	}
	
	
	@SuppressWarnings("unused")
	public static String findTestObject(String location, int nothing)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(RunConfiguration.getProjectDir()+ StringConstants.ID_SEPARATOR 
														+ location + StringConstants.OBJECTS_EXT));
			document.getDocumentElement().normalize();
			Element root = document.getDocumentElement();
			NodeList nList = document.getElementsByTagName("selectorCollection");
			
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
			 Node node = nList.item(temp);
			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {
				Element eElement = (Element) node;
			    return eElement.getElementsByTagName("value").item(0).getTextContent();
			 }
			}
      
			
		}catch (Exception e)
		{
			System.out.println(e);
		}
		return "couldn't find";
	}


	public WebElement getCachedWebElement() {
		// TODO Auto-generated method stub
		return null;
	}


	public long getObjectId() {
		// TODO Auto-generated method stub
		return 0;
	}

}