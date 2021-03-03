package com.console;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import com.configuration.RunConfiguration;
import com.configuration.RunConfiguration.OSType;
import com.constants.StringConstants;


public class Main {
	
	protected static enum Variables{
		PROJECT_VAR,
		TEST_SUITE_VAR,
		PROFILE_VAR,
		BROWSER_VAR,
		REPORT_VAR,
	}
	
	protected static ClassLoader rootClassLoader = Thread.currentThread().getContextClassLoader();
	
	 static final String HEAD = "NRE>";
	 static final String QUIT_COMMAND = "quit";
	 static final String CLEAR_COMMAND = "clear";
	 static final String HELP_COMMAND = "help";
	 static final String PROJECT = "projectPath";
	 static final String TEST_SUITE = "testSuitePath";
	 static final String PROFILE = "executionProfile";
	 static final String BROWSER = "browserType";
     static final String REPORT = "reportFolder";
	 
     //static final String COMMAND = "-testSuitePath=\"Test Suites/Smoke\" -executionProfile=\"default\" -projectPath=\"/home/richard89/Documents/Sebastian-2-master/PCTE-Similar1\" -browserType=\"Chrome\"";
     //static final String COMMAND = "-projectPath=F:\\000\\PCTE-Similar1 -testSuitePath=Smoke -browserType=Chrome(headless)";
     static final String COMMAND = "-projectPath=/home/richard89/Documents/new/Sebastian-2-master2/Sebastian-2-master/PCTE-Similar1 -testSuitePath=Smoke -browserType=Chrome(headless)";
     
	 static HashMap<Variables, String> variables = new HashMap<>();
	 
	 
	 static {
			variables.put(Variables.PROJECT_VAR, null);
			variables.put(Variables.TEST_SUITE_VAR, null);
			variables.put(Variables.PROFILE_VAR, null);
			variables.put(Variables.BROWSER_VAR, null);
			variables.put(Variables.REPORT_VAR, null);
	 }
	 
	public static void main(String[] args) {
		
		RunConfiguration.setPlatform(System.getProperty("os.name"));
		RunConfiguration.setProjectDir(System.getProperty("user.home"));
		
						
		Scanner in = new Scanner(System.in);	
		Boolean quit = Boolean.FALSE;
		
		System.out.println("Welcome to NRE. The new runtime engine for Katalon Studio.");
		
		while(!quit) {
			
			System.out.print(HEAD);
			
			String line = in.nextLine();
			
			if(line.equalsIgnoreCase(QUIT_COMMAND))
				quit = Boolean.TRUE;
			else if(line.equalsIgnoreCase(CLEAR_COMMAND)){
				clearScreen();
			}
			else if(line.equalsIgnoreCase(HELP_COMMAND)){
				displayHelp();
			}
			else if(line.compareTo("123") == 0){	
				//processInput(line);
				//System.out.println(variables);
				
				processInput(COMMAND);
				
				//System.out.println(variables);
				
				if(validateVariables() > 0)
					;
				else {
					
					RunConsole.console();
				}
				
			}
						
		}
		
		in.close();
		System.exit(0);
	}
	
	protected static void processInput(String line) {
			
		variables.clear();
		
		if(validateInput(line) > 0)
			;//do nothing here
		else {
		
		try{
		
			if(RunConfiguration.getPlatform().equals(OSType.WINDOWS)) {
	        	line = line.replace(File.separator, StringConstants.ID_SEPARATOR); 
	        	line = line.replace("/", StringConstants.ID_SEPARATOR);  
	        	
			}
	        
	        else if(RunConfiguration.getPlatform().equals(OSType.LINUX)){
	        	line = line.replace(File.separator, StringConstants.ID_SEPARATOR); 
	        	line = line.replace("\\", StringConstants.ID_SEPARATOR);  
	        }	
			
		String[] arrOfStr = line.split(" -", 9);
        
        for(int i = 0; i < arrOfStr.length; i++) {
        	        	        	
        	arrOfStr[i].trim();
        }
        
        for(int i = 0; i < arrOfStr.length; i++) {
        	if(arrOfStr[i].contains(PROJECT)) {
        		
        		
        		String project = arrOfStr[i].replace("projectPath=", "").replace("\"", "").trim();
        		if(project.startsWith("-"))
        			project = project.substring(1);
        		if(project.contains(".prj"))
        			project = project.substring(0, project.lastIndexOf(StringConstants.ID_SEPARATOR, project.length()));
        		
        		
        		//if(!project.contains(System.getProperty("user.home")))
        			//project = System.getProperty("user.home").concat(StringConstants.ID_SEPARATOR).concat(project);
        		
        		variables.put(Variables.PROJECT_VAR, project);
        		
           		        		
        	}else if(arrOfStr[i].contains(PROFILE)) {
        		
        		String profile = arrOfStr[i].replace("executionProfile=","").replace("\"", "").trim();
        		if(profile.startsWith("-"))
        			profile = profile.substring(1);
        		variables.put(Variables.PROFILE_VAR, profile);       		
        		      		
        	}
        	else if(arrOfStr[i].contains(BROWSER)) {
        		
        		String browser = arrOfStr[i].replace("browserType=", "").replace("\"", "").trim();
        		if(browser.startsWith("-"))
        			browser = browser.substring(1);
        		variables.put(Variables.BROWSER_VAR, browser);
        		        	    		
        	}
        	else if(arrOfStr[i].contains(TEST_SUITE)) {
        		
        		String testSuite = arrOfStr[i].replace("testSuitePath=", "").replace("\"", "").concat(".ts").trim();
        		if(testSuite.startsWith("-"))
        			testSuite = testSuite.substring(1);
        		if(!testSuite.contains(StringConstants.DEFAULT_TEST_SUITES_FOLDER))
        			testSuite = StringConstants.DEFAULT_TEST_SUITES_FOLDER.concat
        						(StringConstants.ID_SEPARATOR).concat(testSuite);
        		        			
        		variables.put(Variables.TEST_SUITE_VAR, testSuite);
        		     		
        	}
          	else if(arrOfStr[i].contains(REPORT)) {
        		
        		String report = arrOfStr[i].replace("reportFolder=", "").replace("\"", "").trim();
        		if(report.startsWith("-"))
        			report = report.substring(1);
        		variables.put(Variables.REPORT_VAR, report);
        		     		
        	}
                
        }
        
		}catch(Exception e) {e.printStackTrace();}
	}
		
	}
	
	
	protected static void clearScreen(){
	    
	    try {
	        if (RunConfiguration.getPlatform().equals(OSType.WINDOWS))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else {
	            System.out.print("\033[H\033[2J");  
	        	System.out.flush();  	        	
	        	//Runtime.getRuntime().exec("clear");
	        }
	    } catch (IOException | InterruptedException ex) {}
	}

	protected static void displayHelp(){
	   
	    try {
	        
	    } catch (Exception ex) {}
	}
	
	
	protected static int validateInput(String input) {
		
		return 0;
	}
	
	protected static int validateVariables() {
		
		int flag = 0;
		
		//validate PROJECT DIR
		if(variables.get(Variables.PROJECT_VAR) != null){
		
			//System.out.println(variables.get(Variables.PROJECT_VAR));
			File projDirectory = new File(variables.get(Variables.PROJECT_VAR));
			if(projDirectory.exists()) {
				RunConfiguration.setProjectDir(variables.get(Variables.PROJECT_VAR));
			
			}else {
				System.out.println("ERROR: Project path does not exist!");
				flag++;
			}
			
			//validate PROFILE
			if(variables.get(Variables.PROFILE_VAR) == null)
				variables.put(Variables.PROFILE_VAR, StringConstants.DEFAULT_PROFILE);
			
			File profileFile = new File(RunConfiguration.getProjectDir() + StringConstants.ID_SEPARATOR 
					+ StringConstants.PROFILE_FOLDER + StringConstants.ID_SEPARATOR 
					+ variables.get(Variables.PROFILE_VAR) + StringConstants.PROFILE_EXT);
			if(profileFile.isFile()) {
				RunConfiguration.setProfile(variables.get(Variables.PROFILE_VAR));
				
			}else {
				System.out.println("ERROR: Profile '"+ variables.get(Variables.PROFILE_VAR) + "' does not exist!");
				flag++;
			}
			//-------------------------------------------------------------------------

			
			//validate BROWSER
			if(variables.get(Variables.BROWSER_VAR) == null)
				variables.put(Variables.BROWSER_VAR, StringConstants.DEFAULT_BROWSER);
			RunConfiguration.setBrowser(variables.get(Variables.BROWSER_VAR));
			//-------------------------------------------------------------------------

			
			//validate REPORT DIR---------------------------------------------------
			if(variables.get(Variables.REPORT_VAR) == null)
				variables.put(Variables.REPORT_VAR, RunConfiguration.getProjectDir() + StringConstants.ID_SEPARATOR 
						+ StringConstants.DEFAULT_REPORT_DIR);
		
			new File(variables.get(Variables.REPORT_VAR)).mkdir();
			
				RunConfiguration.setReportDir(variables.get(Variables.REPORT_VAR));
			//-------------------------------------------------------------------------
			
				
			//validate TEST SUITE---------------------------------------------------
			if(variables.get(Variables.TEST_SUITE_VAR) != null) {
				
				variables.put(Variables.TEST_SUITE_VAR, RunConfiguration.getProjectDir().
						concat(StringConstants.ID_SEPARATOR.concat(variables.get(Variables.TEST_SUITE_VAR))));
				
				RunConfiguration.setTestSuite(variables.get(Variables.TEST_SUITE_VAR));
				
			}else {
				System.out.println("ERROR: No test suite was provided!");
				flag++;
			}
			//-------------------------------------------------------------------------

		}else{
			System.out.println("ERROR: No project path was provided!");
			flag++;
		}
		//-------------------------------------------------------------------------

			return flag;	
	}
	
	
	public static ClassLoader getRootClassLoader() {
				
		return rootClassLoader;
		
	}
	
}
