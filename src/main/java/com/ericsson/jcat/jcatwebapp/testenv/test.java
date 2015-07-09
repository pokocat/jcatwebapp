package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.EnumSet;
import java.util.Iterator;



import org.apache.commons.lang.StringUtils;

import com.ericsson.jcat.jcatwebapp.enums.ServerAction;
import com.ericsson.jcat.jcatwebapp.enums.TestingTool;
import com.ericsson.jcat.jcatwebapp.enums.TestingTool.TestingToolGroup;

public class test {

	public static void main(String[] args) {
		for (TestingToolGroup testingTool : TestingTool.TestingToolGroup.values()) {
			System.out.println(ServerAction.valueOf(StringUtils.upperCase("start")));
		}
		
		
	}
}
