package com.scripts;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.tools.GroovyClass;

import com.configuration.RunConfiguration;
import com.constants.StringConstants;
import com.util.DirectoryUtils;
import com.console.Main;
import groovy.lang.GroovyClassLoader;




public class StepDefinitionsFactory {

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
											
	private static ArrayList<File> listOfScripts = null;
	public static ArrayList<File> listOfSteps = null;
		
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
		listOfScripts = getScriptFiles();
		
		listOfSteps = new ArrayList<>();
		//System.out.println(listOfScripts.size()); debugging mode
		
		String className = null;
		String scriptSource = null;
		
		
		for(int i = 0; i < listOfScripts.size(); i++) {


			//String temp = getScript(listOfScripts.get(i));	//debugging mode
			//System.out.println(temp);	//debugging mode

			listOfSteps.add(i, getScript(listOfScripts.get(i)));
			
			String name = listOfSteps.get(i).getName().replace(StringConstants.GROOVY_EXT, "");
			
			if(name.contains("TestSupport") || name.contains("SharedTestData")) { 
				flag = Boolean.TRUE;
				className = StringConstants.HOOKS+ "." + listOfSteps.get(i).getName().replace(StringConstants.GROOVY_EXT, "");
				 
			}else
				className = StringConstants.STEP_DEFS_GLUE + "." + listOfSteps.get(i).getName().replace(StringConstants.GROOVY_EXT, "");
			
			
			scriptSource = String.join(StringConstants.NEW_LINE ,readLines(listOfSteps.get(i)));
			
			//System.out.println(scriptSource);
			
			 compileGroovyScript(className, scriptSource);
			 
			 if(flag) {
				
				 DirectoryUtils.copyDirectory("hooks", "bin"+StringConstants.ID_SEPARATOR+"hooks");
			 }
				 
										
		}
		
		
	}
	

	private static ArrayList<File> getScriptFiles() {

		ArrayList<File> scripts = new ArrayList<>();

		String folder = RunConfiguration.getProjectDir() + StringConstants.SCRIPTS_SOURCE;
		File fld = new File(folder);

		//System.out.println(fld.toString()); //debugging mode

		while(fld.isDirectory()) {

			File[] ls = fld.listFiles();

			for(File file : ls) {

				if(!file.getName().contains(StringConstants.CUSTOM_ATTRIBUTES_FILE))
					if(!file.isDirectory()) {
						scripts.add(file);
						fld = file;
					}
					else
						fld = file;
			}

		}

		return scripts;
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
			
		}
		
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
		
			
		new File(StringConstants.ROOT_DIR + StringConstants.ID_SEPARATOR + StringConstants.SCRIPTS_FOLDER).mkdirs();

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
	
	@SuppressWarnings("rawtypes")
	public static Class getGroovyScript(final String className, String script) {
	    Class clazz = null;

	    try (GroovyClassLoader classLoader = new GroovyClassLoader(Main.getRootClassLoader())) {
	        clazz = classLoader.parseClass(script, className);
	                	          
	        //classLoader.addClasspath(className);
	        
	        
	        
	    } catch (IOException e) {
	    } catch (Exception e) {
	    }

	    return clazz;
	}
	
	

}
