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
			try {
			groovyShell.parse(script).invokeMethod("setup", null);
			}catch(Exception e)
			{
				String name = e.getClass().getName();
				if(name.equals("org.codehaus.groovy.runtime.metaclass.MissingMethodExceptionNoStack"))
				{
					// do nothing
				}else
				{
					Assert.fail("setup() failed:" + e.toString());
				}
				
			}
			
			
			groovyShell.evaluate(script);
			
			try {
				groovyShell.parse(script).invokeMethod("teardown", null);
				}catch(Exception e)
				{
					String name = e.getClass().getName();
					if(name.equals("org.codehaus.groovy.runtime.metaclass.MissingMethodExceptionNoStack"))
					{
						// do nothing
					}else
					{
						Assert.fail("teardown() failed:" + e.toString());
					}
					
				}
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
