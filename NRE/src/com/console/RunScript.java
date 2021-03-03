package com.console;

import java.io.File;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import groovy.lang.GroovyShell;


public class RunScript {
	
	 @BeforeClass  
	 public void SetUp() {}  
	  
	 
	 @AfterClass  
	 public void TearDown() {}  
	  
	   
	
	@Parameters({"path"})
	@Test
	public static void runScript(String path)
	{
		File script = new File(path);
		
		GroovyShell groovyShell = new GroovyShell();
		
		try {
			
			//System.out.println(script.getAbsolutePath());
			
			groovyShell.evaluate(script);
		}catch(Exception e)
		{
			Assert.fail(e.toString());
		}
	}
	
	public static Object getObject(String defaultValue)
	{
		ImportCustomizer importCustomizer = new ImportCustomizer();

        importCustomizer.addStaticImport("com.ucf.pcte.TestDataFinder", "findTestData");
		 
		CompilerConfiguration configuration = new CompilerConfiguration();
		configuration.addCompilationCustomizers(importCustomizer);
		String script = defaultValue;
		GroovyShell groovyShell = new GroovyShell(configuration);
		Object output = null;
		
		try {
			output = groovyShell.evaluate(script);
		}catch(Exception e)
		{
			Assert.fail(e.toString());
		}
		
		
		return output;
	}
}
