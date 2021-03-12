package com.console;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.configuration.RunConfiguration;
import com.constants.StringConstants;
import com.scripts.*;
import com.util.DirectoryUtils;



public class RunConsole {
	
	
	public static ArrayList<String> testCases = new ArrayList<String>();
	public static Map<String,Object> GlobalVariables;
	
		

	public static void console() {
		
		
		GlobalVariables = GlobalVars.getGlobalVars(RunConfiguration.getProfile());
		//GlobalVariables.forEach((key, value) -> System.out.println(key + ":" + value + "class:" + value.getClass().getName()));
		GlobalVariableFactory.createGlobalVarFile(GlobalVariables);
		
		DirectoryUtils.copyDirectory("internal", "bin"+StringConstants.ID_SEPARATOR+"internal");
		DirectoryUtils.deleteDirectory("internal");
		
		try {
			
			FeaturesFactory.copyFeatures();
			StepDefinitionsFactory.copyScripts();
			if(new File(StringConstants.ROOT_DIR+StringConstants.ID_SEPARATOR+StringConstants.TEMP_COMPILED_STEPS_FOLDER).exists())
				DirectoryUtils.copyDirectory(StringConstants.ROOT_DIR+StringConstants.ID_SEPARATOR+StringConstants.TEMP_COMPILED_STEPS_FOLDER,
										StringConstants.ROOT_DIR+StringConstants.ID_SEPARATOR+StringConstants.COMPILED_STEPS_FOLDER);
			if(new File("internal").exists())
			DirectoryUtils.deleteDirectory("com");
			DirectoryUtils.deleteDirectory("hooks");
				
			TestCaseFactory.copyTestCaseScripts();
			TestCaseFactory.changeImports();
						
			setTempFiles();			
			
						
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		
		TestCases.getTestCases(RunConfiguration.getTestSuite());
		
		/*
		for(String test : testCases) {
			
			System.out.println(test.toString());
		}*/
		
		
	
		try 
		{
			Execute.run();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		try {
			
			cleanProject();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	private static void cleanProject() throws IOException {
		
		setTempFiles();
		
		DirectoryUtils.deleteDirectory("com");
		DirectoryUtils.deleteDirectory("hooks");
		DirectoryUtils.deleteDirectory("features");
		DirectoryUtils.deleteDirectory("bin"+StringConstants.ID_SEPARATOR+"com"+StringConstants.ID_SEPARATOR+"stepdefinitions");
		DirectoryUtils.deleteDirectory("bin"+StringConstants.ID_SEPARATOR+"hooks");
		DirectoryUtils.deleteDirectory("temp");
		DirectoryUtils.deleteDirectory("bin"+StringConstants.ID_SEPARATOR+"internal");
							
	}
	
	
	private static void setTempFiles() {
		
		
		/*for(File file : StepDefinitionsFactory.listOfScripts) {
			
			file.deleteOnExit();
		}*/
	
		for(File file : FeaturesFactory.listOfNewFeatures) {
	
			file.deleteOnExit();
		}
		
	}
	
}
