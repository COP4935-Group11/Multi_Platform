package com.ucf.pcte;

import org.junit.Assert;
import cucumber.api.cli.Main;


import com.constants.StringConstants;

import com.console.Execute;


public class CucumberKW {
	
//	private static final KeywordLogger logger = KeywordLogger.getInstance(CucumberKW.class);
	
	
	public static void runFeatureFile(String relativeFilePath)
	{
				
		relativeFilePath = StringConstants.FEATURES_FOLDER.concat(relativeFilePath
				.substring(relativeFilePath.lastIndexOf('/'), relativeFilePath.length()));
		
		//System.out.println(relativeFilePath);
		
		System.out.println("Starting run keyword runFeatureFile: " + relativeFilePath 
				+ " and extract report to folder: " + Execute.reportDir + "...");
		
		
		String[] argv = new String[]{
				relativeFilePath,
				"--glue",
				StringConstants.STEP_DEFS_GLUE,
				"--no-strict",
                "-p",
                "pretty",
                "-p",
    			"junit:"+ Execute.reportDir + StringConstants.ID_SEPARATOR + System.currentTimeMillis() + StringConstants.XML_EXT
                };
		
		
	

		boolean runSuccess = Main.run(argv, Thread.currentThread().getContextClassLoader()) == 0;
	
			if (runSuccess) {
				System.out.println("Feature file: "+ relativeFilePath + " was passed");
			} else {
				Assert.fail("Cucumber feature file failed.");
				System.out.println("Feature file: "+ relativeFilePath + " failed");
			}
		
	}
	
}
