package com.console;

import groovy.util.Node;
import groovy.util.NodeList;
import groovy.xml.XmlParser;
import java.io.*;

import com.configuration.RunConfiguration;

public class TestCases {
	
	public static void getTestCases(String testSuite)
	{
		try
		{
			Node rootNode = new XmlParser()
                    .parse(new File(RunConfiguration.getTestSuite()));
            NodeList variableNodes = (NodeList) rootNode.get("testCaseLink");
            for (int index = 0; index < variableNodes.size(); index++) {
                Node globalVariableNode = (Node) variableNodes.get(index);
                RunConsole.testCases.add(((Node) ((NodeList) globalVariableNode.get("testCaseId")).get(0)).text());
                
            }
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}

