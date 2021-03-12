package com.scripts;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.tools.GroovyClass;

import com.configuration.RunConfiguration;
import com.constants.StringConstants;
import com.util.DirectoryUtils;




public class StepDefinitionsFactory {
	
	static class Script{
		
		File script;
		Boolean changed;
		
	}
		

	private static final String TESTSUPPORT_IMP = "import hooks.TestSupport";
	private static final String SHAREDTESTDATA_IMP = "import hooks.SharedTestData";
	private static final String WEBUICOMMONHELPER_IMP = "import com.aux.WebUiCommonHelper";
	private static final String CONDTYPE_IMP = "import com.aux.ConditionType";
	private static final String KEYWORDUTIL_IMP = "import com.aux.KeywordUtil";
	private static final String TESTOBJECT_IMP = "import com.ucf.pcte.gold.TestObject";
	private static final String FAILUREHANDLING_IMP = "import com.ucf.pcte.gold.FailureHandling";
	private static final String CRYPTOGRAPHER_IMP = "import edu.ucf.irl.cryptographer.Cryptographer";
	private static final ArrayList<String> GA_IMP = new ArrayList<>(
									List.of("import com.warrenstrange.googleauth.GoogleAuthenticator",
											"import com.warrenstrange.googleauth.GoogleAuthenticatorConfig",
											"import com.warrenstrange.googleauth.GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder",
											"import com.warrenstrange.googleauth.HmacHashFunction"));;
											
	private static final ArrayList<String> AWT_IMP = new ArrayList<>(
									List.of("import java.awt.Robot",
											"import java.awt.Toolkit",
											"import java.awt.datatransfer.StringSelection",
											"import java.awt.event.KeyEvent"));;
											
	private static LinkedList<Script> listOfScripts = null;
	//private static ArrayList<File> listOfSteps = null;
		
	private static ArrayList<String> imports;

//          private static ArrayList<String> stepImports = new ArrayList<>(); 
//        		  	List.of(StringConstants.DEFAULT_SCRIPTS_PACKAGE,
//        		  			"import com.ucf.pcte.CucumberKW", 
//					"import com.ucf.pcte.gold.WebUI as WebUI", 
//					"import static com.ucf.pcte.gold.WebUI.findTestObject",
//					"import cucumber.api.java.en.*",
//					"import com.kms.katalon.core.testdata.CSVData",
//					"import com.kms.katalon.core.testdata.reader.CSVSeparator",
//					"import com.configuration.RunConfiguration"));;

	
	public static void copyScripts() throws IllegalAccessException, InstantiationException, 
											IOException, ClassNotFoundException {

		Boolean flag = Boolean.FALSE;
		Boolean flag2 = Boolean.FALSE;
		listOfScripts = new LinkedList<>();
		
		//listOfSteps = new ArrayList<>();
		
		
		String className = null;
		String scriptSource = null;
		File tempFile = null;
		Script tempScript = null;
		
		
		getScriptFiles();
		
		//System.out.println(listOfScripts.size()); //debugging mode
		
		while(!listOfScripts.isEmpty()) {

			//String temp = getScript(listOfScripts.get(i));	//debugging mode
			//System.out.println(temp);	//debugging mode

			//listOfSteps.add(i, getScript(listOfScripts.get(i)));
			tempScript = createScript(listOfScripts.getFirst().script);
			tempFile = getScript(listOfScripts.getFirst().script);
			
			String name = tempFile.getName().replace(StringConstants.GROOVY_EXT, "");
			
			if(name.contains("TestSupport") || name.contains("SharedTestData")) { 
				flag = Boolean.TRUE;
				className = StringConstants.HOOKS+ "." + name;
				 
			}else
				className = StringConstants.STEP_DEFS_GLUE + "." + name;
			
			
			scriptSource = String.join(StringConstants.NEW_LINE ,readLines(tempFile));
			
			//System.out.println(scriptSource);
			try {
			
				compileGroovyScript(className, scriptSource);
			 
			}catch(Exception e) {
				
				listOfScripts.removeFirst();
				tempScript.changed = Boolean.TRUE;
				listOfScripts.add(1, tempScript);
				flag = Boolean.FALSE;
				flag2 = Boolean.TRUE;
			}
			 if(flag) {
				
				 DirectoryUtils.copyDirectory("hooks", "bin"+StringConstants.ID_SEPARATOR+"hooks");
			 }
				 
				
			 if(!flag2) {
				 listOfScripts.removeFirst();
			 }
		}
		
		
	}
	

	private static void  getScriptFiles() {

		//ArrayList<File> scripts = new ArrayList<>();

		//String folder = RunConfiguration.getProjectDir() + StringConstants.SCRIPTS_SOURCE;
		File folder = new File(RunConfiguration.getProjectDir() + StringConstants.SCRIPTS_SOURCE);
	
		//System.out.println(fld.toString()); //debugging mode
	
			readFolder(folder);
			
		//return scripts;
	}

	static File getScript(File rootScript) throws IOException {

		String sourceCode= null;
		Boolean cryptoFlag = Boolean.FALSE;
		Boolean googleAuthFlag = Boolean.FALSE;
		Boolean toFlag = Boolean.FALSE;
		Boolean fhFlag = Boolean.FALSE;
		Boolean ctFlag = Boolean.FALSE;
		Boolean kuFlag = Boolean.FALSE;
		Boolean wchFlag = Boolean.FALSE;
		Boolean tsFlag = Boolean.FALSE;
		Boolean stdFlag = Boolean.FALSE;
		Boolean awtFlag = Boolean.FALSE;
		Boolean isTestSupport = Boolean.FALSE;
		Boolean isSharedTestData = Boolean.FALSE;
		
		
		
		ArrayList<String> fileContent = readLines(rootScript);
		ArrayList<String> script = new ArrayList<>();
		
		imports = new ArrayList<>(Arrays.asList(StringConstants.IMPORTS));					
		
		Iterator<String> iter = fileContent.iterator();
		while (iter.hasNext()) {
		  String line = iter.next();
		  if (line.contains("package")) iter.remove();
		  else if(line.contains("import")) {
			  if(line.contains("cryptographer"))
				  cryptoFlag = Boolean.TRUE;
			  if(line.contains("googleauth"))
				  googleAuthFlag = Boolean.TRUE;
			  if(line.contains("TestObject"))
				  toFlag = Boolean.TRUE;
			  if(line.contains("FailureHandling"))
				  fhFlag = Boolean.TRUE;
			  if(line.contains("ConditionType"))
				  ctFlag = Boolean.TRUE;
			  if(line.contains("KeywordUtil"))
				  kuFlag = Boolean.TRUE;
			  if(line.contains("WebUiCommonHelper"))
				  wchFlag = Boolean.TRUE;
			  if(line.contains("TestSupport"))
				  tsFlag = Boolean.TRUE;
			  if(line.contains("SharedTestData"))
				  stdFlag = Boolean.TRUE;
			  if(line.contains("java.awt."))
				  awtFlag = Boolean.TRUE;
			  
			  iter.remove();
		  }
		  
		  if(line.contains("public class SharedTestData"))
			  isSharedTestData = Boolean.TRUE;
		  if(line.contains("public class TestSupport"))
			  isTestSupport = Boolean.TRUE;
		  
		}
		
		
		
		if(isTestSupport || isSharedTestData) {
					
			imports.set(0, StringConstants.DEFAULT_HOOKS_PACKAGE);
			
			if(isSharedTestData)
				tsFlag = Boolean.TRUE;
			
		}else
			imports.set(0, StringConstants.DEFAULT_SCRIPTS_PACKAGE);
		
		script.addAll(imports);
		
		if(ctFlag)
			script.add(CONDTYPE_IMP);
		if(wchFlag)
			script.add(WEBUICOMMONHELPER_IMP);
		if(kuFlag)
			script.add(KEYWORDUTIL_IMP);
		if(toFlag)
			script.add(TESTOBJECT_IMP);
		if(fhFlag)
			script.add(FAILUREHANDLING_IMP);
		if(cryptoFlag)
			script.add(CRYPTOGRAPHER_IMP);
		if(tsFlag)
			script.add(TESTSUPPORT_IMP);
		if(stdFlag)
			script.add(SHAREDTESTDATA_IMP);
		if(googleAuthFlag)
			script.addAll(GA_IMP);
		if(awtFlag)
			script.addAll(AWT_IMP);
		
		
				
		script.addAll(fileContent);
							
		sourceCode = String.join(StringConstants.NEW_LINE, script);
		
		//System.out.println(StringConstants.ROOT_DIR + StringConstants.SCRIPTS_FOLDER);
			
		new File(StringConstants.ROOT_DIR + StringConstants.SCRIPTS_FOLDER).mkdirs();

		//System.out.println(StringConstants.ROOT_DIR + StringConstants.SCRIPTS_FOLDER 
		//		+ StringConstants.ID_SEPARATOR + rootScript.getName());
		
		Path targetPath = Paths.get(new File(StringConstants.ROOT_DIR + StringConstants.SCRIPTS_FOLDER 
											+ StringConstants.ID_SEPARATOR + rootScript.getName()).getAbsolutePath());
		Files.writeString(targetPath, sourceCode, StringConstants.STANDARD_CHARSET);
		
		
		File filee = new File(targetPath.toAbsolutePath().toString());
		//System.out.println(filee);
		
		//ImportFactory.removeAndAdd(filee);

		return 	filee;
	}
	
		
	
	public static ArrayList<String> readLines(File rootScript) throws IOException{
		
		ArrayList<String> fileContent = new ArrayList<String>(Files.readAllLines(Paths.get(rootScript.toURI()), 
																StringConstants.STANDARD_CHARSET));
	
		return fileContent;
	}
	
	@SuppressWarnings("unused")
	public static void compileGroovyScript(final String className, final String script) {
	    //byte[] compiledScriptBytes = null;
		CompilationUnit compileUnit = new CompilationUnit();
	    			    
	    compileUnit.addSource(className, script);
	    compileUnit.compile(Phases.OUTPUT);

	    for (Object compileClass : compileUnit.getClasses()) {
	        GroovyClass groovyClass = (GroovyClass) compileClass;
	        //compiledScriptBytes = groovyClass.getBytes();
	    }

	}
	
	private static void readFolder(File folder) {
		
			//create filters for iterating folders and files
			FileFilter folderFilter = new FileFilter() {
	            @Override
	            public boolean accept(File pathname) {
	               return pathname.isDirectory();
	            }
	         };
	         
	         FileFilter fileFilter = new FileFilter() {
		            @Override
		            public boolean accept(File pathname) {
		               return pathname.isFile();
		            }
		         };
		         
		    //Verify if there are any files in the folder
	         File[] lsFiles = folder.listFiles(fileFilter);
	       //Verify if the folder contains nested folders
	         File[] ls = folder.listFiles(folderFilter);
	         
	         	         
	         //copy the files first (to handle hooks properly)
	         for(int i = 0; i < lsFiles.length; i++) {
	        	 if(!lsFiles[i].getName().contains(StringConstants.CUSTOM_ATTRIBUTES_FILE))
						listOfScripts.addLast(createScript(lsFiles[i]));
	         }
	         
	         //if there are any containing folder, repeat the recursion
	         if(ls.length > 0)
	        	 readFolder(ls[0]);
	         
	}
	
	public static Script createScript(File file) {
		
		Script newScript = new Script();
		
		newScript.script = file;
		newScript.changed = Boolean.FALSE;
		
		return newScript; 
	}
	
	public static Script createScript(File file, Boolean c) {
		
		Script newScript = new Script();
		
		newScript.script = file;
		newScript.changed = c;
		
		return newScript; 
	}
	

}
