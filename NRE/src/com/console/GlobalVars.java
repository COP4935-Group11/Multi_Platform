package com.console;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.util.Node;
import groovy.util.NodeList;
import groovy.xml.XmlParser;

import com.configuration.RunConfiguration;
import com.constants.StringConstants;


@SuppressWarnings("unused")
public class GlobalVars{

	
	public static Map<String, Object> getGlobalVars(String profileName) {
				
		try {
            Map<String, Object> selectedVariables = new HashMap<>();
            Node rootNode = new XmlParser()
                    .parse(new File(RunConfiguration.getProjectDir() + StringConstants.ID_SEPARATOR 
        					+StringConstants.PROFILE_FOLDER + StringConstants.ID_SEPARATOR 
        					+RunConfiguration.getProfile() + StringConstants.PROFILE_EXT));
            NodeList variableNodes = (NodeList) rootNode.get("GlobalVariableEntity");
            for (int index = 0; index < variableNodes.size(); index++) {
                Node globalVariableNode = (Node) variableNodes.get(index);
                String variableName = ((Node) ((NodeList) globalVariableNode.get("name")).get(0)).text();
                String defaultValue = ((Node) ((NodeList) globalVariableNode.get("initValue")).get(0)).text();
                try {
                	selectedVariables.put(variableName, RunScript.getObject(defaultValue));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return selectedVariables;
        } catch (Exception ex) {
            System.out.println("Could not create global variable.");
            return Collections.emptyMap();
        }  
    }

}

	

