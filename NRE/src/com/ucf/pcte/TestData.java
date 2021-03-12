package com.ucf.pcte;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.configuration.RunConfiguration;
import com.constants.StringConstants;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;


public class TestData {
	String type = null;
	String path = null;
	String seperator = null;
	boolean isInternal = true;
	boolean contHeader = true;
	
	
	public TestData(String path, String type, Boolean contHead, Boolean isInternal, String seperator)
	{
		this.path = path;
		this.type = type;
		this.contHeader = contHead;
		this.isInternal = isInternal;
		this.seperator = seperator;
	}
	
	public Object getValue(int column, int row)
	{
		Object value = null;
		
		if(isInternal == true)
		{
			path = RunConfiguration.getProjectDir() + StringConstants.ID_SEPARATOR + path;
		}
		
		switch (type)
		{
			case "CSV":
				return getCSVData(column, row);
				
			default:
				break;
		}
		
		
		return value;
		
	}
	
	public Object getValue(String column, int row)
	{
		Object value = null;
		
		if(isInternal == true)
		{
			path = RunConfiguration.getProjectDir() + StringConstants.ID_SEPARATOR + path;
		}
		
		switch (type)
		{
			case "CSV":
				return getCSVData(column, row);
				
			default:
				break;
		}
		
		
		return value;
		
	}
	
	public Object getCSVData(int column, int row)
	{
		
		 try { 
		        FileReader filereader = new FileReader(path); 
		  
		        CSVParser parser = new CSVParserBuilder().withSeparator(',').build(); 
		   
		        CSVReader csvReader = new CSVReaderBuilder(filereader) 
		                                  .withCSVParser(parser) 
		                                  .build(); 
		  
		        List<String[]> allData = csvReader.readAll();
		        
//		        for(String[] strings: allData)
//		        {
//		        	for(String string: strings )
//		        	{
//		        		System.out.println(string);
//		        	}
//		        }
		        return allData.get(row)[column-1];
		        
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
		return "Could not get value of Test Data";
		
	}
	
	public Object getCSVData(String column, int row)
	{
		
		 try { 
		        FileReader filereader = new FileReader(path); 
		  
		        CSVParser parser = new CSVParserBuilder().withSeparator(',').build(); 
		   
		        CSVReader csvReader = new CSVReaderBuilder(filereader) 
		                                  .withCSVParser(parser) 
		                                  .build(); 
		  
		        List<String[]> allData = csvReader.readAll();
		        
		        
//		        for(String[] strings: allData)
//		        {
//		        	for(String string: strings )
//		        	{
//		        		System.out.println(string);
//		        	}
//		        }
		        
//		        System.out.println("**"+allData.get(row)[getColumnIndex(column,allData)]);
		        return allData.get(row)[getColumnIndex(column,allData)];
		        
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
		return "Could not get value of Test Data";
		
	}
	
	public int getColumnIndex(String column, List<String[]> allData)
	{
		for(int i = 0; i < allData.size(); i++)
		{
			if(allData.get(0)[i].equals(column)) 
			{
				return i;
			}
		}
		
		
		
		
		return 0;
	}
	
	public int getRowNumbers()
	{
		FileReader filereader = null;
		try {
			filereader = new FileReader(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build(); 
   
        CSVReader csvReader = new CSVReaderBuilder(filereader) 
                                  .withCSVParser(parser) 
                                  .build(); 
        List<String[]> allData = null;
        try {
			allData = csvReader.readAll();
		} catch (IOException | CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return allData.size();
	}

}