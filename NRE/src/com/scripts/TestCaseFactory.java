package com.scripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.configuration.RunConfiguration;
import com.constants.StringConstants;

public class TestCaseFactory {
	
	public static void copyTestCaseScripts()
	{
		//String source = StringConstants.TESTCASE_SCRIPTS_SOURCE;
		File srcDir = new File(RunConfiguration.getProjectDir()+StringConstants.TESTCASE_SCRIPTS_SOURCE);

		//String destination = "temp/Scripts";
		File destDir = new File(StringConstants.TESTCASE_SCRIPTS_FOLDER);

		try {
		    FileUtils.copyDirectory(srcDir, destDir);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static List<File> files;
	public static void changeImports()
	{
		
        
        files = listf("temp"+StringConstants.ID_SEPARATOR+"Scripts");
        
        removeAndAdd(files);
	}
	
	public static List<File> listf(String directoryName) {
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.getName().endsWith((StringConstants.GROOVY_EXT)))
            {
                resultList.add(file);
            }
            else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }
        return resultList;
    }
	
	public static void removeAndAdd(List<File> files)
    {
        for(File file: files)
        {
            List<String> lines = new ArrayList<String>();
            String line = null;
            try {
                File f1 = file;
                FileReader fr = new FileReader(f1);
                BufferedReader br = new BufferedReader(fr);

                line = br.readLine();
                if (line.contains("package"))
                {
                    lines.add(line);
                    lines.add(StringConstants.NEW_LINE);
                    line = br.readLine();
                }

                lines.add(getImports(StringConstants.IMPORTS));
                
                               
                while ((line = br.readLine()) != null) {
                    if (line.contains("import") || line.contains("@com.kms.katalon.core.annotation"))
                    {
                        continue;
                    }
                        
                    lines.add(line);
                    lines.add(StringConstants.NEW_LINE);
                }

                fr.close();
                br.close();
    
                FileWriter fw = new FileWriter(f1);
                BufferedWriter out = new BufferedWriter(fw);
                for(String s : lines)
                     out.write(s);
                out.flush();
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
	
	public static String getImports(String[] imports)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i<imports.length;i++)
		{
		    sb.append(imports[i]);
		    sb.append(StringConstants.NEW_LINE);
		}
		
		 //add imports for hooks
        if(new File(StringConstants.ROOT_DIR + "bin" + StringConstants.ID_SEPARATOR + StringConstants.HOOKS).exists()) { 
        	sb.append("import hooks.TestSupport");
        	sb.append(StringConstants.NEW_LINE);
        	sb.append("import hooks.SharedTestData");
        	sb.append(StringConstants.NEW_LINE);
        }
		
		return sb.toString();
	}
	

}
