package com.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.configuration.RunConfiguration;
import com.configuration.RunConfiguration.OSType;


public class StringConstants {
	
	public static String ID_SEPARATOR = getSeparator();
	public static String ROOT_DIR = System.getProperty("user.dir")+ID_SEPARATOR;
	public static String HOME_DIR = System.getProperty("user.home")+ID_SEPARATOR;
	public static String PROFILE_FOLDER = "Profiles";
	public static String DEFAULT_REPORT_DIR = "cucumber_report";
	public static String DEFAULT_PROFILE = "default";
	public static String DEFAULT_TEST_SUITES_FOLDER = "Test Suites";
	public static String DEFAULT_BROWSER = "firefox(headless)";
	public static String FEATURES_FOLDER = "temp"+ID_SEPARATOR+"features";
	public static String FEATURES_SOURCE = "Include"+ID_SEPARATOR+"features";
	public static String DATA_FILES_FOLDER = "Data Files";
	public static String TEMP_COMPILED_STEPS_FOLDER = "com"+ID_SEPARATOR+"stepdefinitions";
	public static String COMPILED_STEPS_FOLDER = "bin"+ID_SEPARATOR+"com"+ID_SEPARATOR+"stepdefinitions";
	public static String NEW_LINE = "\n";
	public static String SCRIPTS_SOURCE = ID_SEPARATOR+"Include"+ID_SEPARATOR+"scripts"+ID_SEPARATOR;
	public static String CUSTOM_ATTRIBUTES_FILE = "DS_Store";
	public static String GROOVY_EXT = ".groovy";
	public static String XML_EXT = ".xml";
	public static String PROFILE_EXT = ".glbl";
	public static String DATA_FILES_EXT = ".dat";
	public static String OBJECTS_EXT = ".rs";
	public static String DEFAULT_SCRIPTS_PACKAGE = "package com.stepdefinitions";
	public static Charset STANDARD_CHARSET = StandardCharsets.UTF_8;
	public static String STEP_DEFS_GLUE = "com.stepdefinitions";
	
	public static String SCRIPTS_FOLDER = "temp"+ID_SEPARATOR+"scripts";
	public static String TESTCASE_SCRIPTS_SOURCE = ID_SEPARATOR+"Scripts"+ID_SEPARATOR;
	public static String TESTCASE_SCRIPTS_FOLDER = "temp"+ID_SEPARATOR+"Scripts"+ID_SEPARATOR;
	
	public static String[] IMPORTS = {StringConstants.DEFAULT_SCRIPTS_PACKAGE,
			"import com.ucf.pcte.CucumberKW", 
			"import com.ucf.pcte.gold.WebUI as WebUI", 
			"import static com.ucf.pcte.gold.WebUI.findTestObject",
			"import cucumber.api.java.en.*",
			"import com.ucf.pcte.CSVData",
			"import com.constants.CSVSeparator",
			"import com.configuration.RunConfiguration",
			"import internal.GlobalVariable",
			"import com.constants.*",
			"import com.annotation.*"};


	protected static final String getSeparator() {
		
		if(RunConfiguration.getPlatform().compareTo(OSType.WINDOWS) == 0)
			return new String("\\");
		else
			return new String("/");	
	}
	

}
