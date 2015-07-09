package com.ericsson.jcat.jcatwebapp.testenv;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ericsson.jcat.jcatwebapp.enums.TestToolType;
import com.ericsson.jcat.jcatwebapp.enums.TestingTool;
import com.ericsson.jcat.jcatwebapp.enums.TrafficGenerator;

@Entity
public class TestTool implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private TestToolType toolType;

	private TestingTool toolName;

	private String toolId;

	public TestTool() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestTool(TestToolType toolType, TestingTool toolName, String toolId) {
		super();
		this.toolType = toolType;
		this.toolName = toolName;
		this.toolId = toolId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TestToolType getToolType() {
		return toolType;
	}

	public void setToolType(TestToolType type) {
		this.toolType = type;
	}

	public TestingTool getToolName() {
		return toolName;
	}

	public void setToolName(TestingTool toolName) {
		this.toolName = toolName;
	}

	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	@Override
	public String toString() {
		return "TestTool [id=" + id + ", " + (toolType != null ? "toolType=" + toolType + ", " : "")
				+ (toolName != null ? "toolName=" + toolName + ", " : "") + (toolId != null ? "toolId=" + toolId : "")
				+ "]";
	}
}
