package com.aux;


import java.util.List;

import java.util.logging.Logger;

import org.openqa.selenium.WebElement;

import com.exception.WebElementNotFoundException;
import com.ucf.pcte.TestObject;
//import org.openqa.selenium.support.ui.Select;


@SuppressWarnings({ "unused"})
public class WebUiCommonHelper {
    
    private static final Logger logger = Logger.getLogger(WebUiCommonHelper.class.getName());
    
    public static final String CSS_LOCATOR_PROPERTY_NAME = "css";
    
    public static final String XPATH_LOCATOR_PROPERTY_NAME = "xpath";

    public static final String WEB_ELEMENT_TAG = "tag";

    public static final String WEB_ELEMENT_ATTRIBUTE_LINK_TEXT = "link_text";

    public static final String WEB_ELEMENT_ATTRIBUTE_TEXT = "text";

    public static final String WEB_ELEMENT_XPATH = "xpath";

    
    public static WebElement findWebElement(TestObject testObject, int timeOut) throws Exception {
        WebElement cachedWebElement = testObject.getCachedWebElement();
        if (cachedWebElement != null) {
            //logger.logDebug("Using cached web element");
            return cachedWebElement;
        }
        //logger.logDebug("Finding web element from Test Object's properties");
        List<WebElement> elements = findWebElements(testObject, timeOut);
        if (elements != null && elements.size() > 0) {
            return elements.get(0);
        } else {
            //String locator = getSelectorValue(testObject);
            throw new WebElementNotFoundException("Error: WebElement Not Found");
        }
    }


	private static String getSelectorValue(TestObject testObject) {
		// TODO Auto-generated method stub
		return null;
	}


	private static List<WebElement> findWebElements(TestObject testObject, int timeOut) {
		// TODO Auto-generated method stub
		return null;
	}
}

    