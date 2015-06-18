package com.ericsson.jcat.jcatwebapp.testenv;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TestTool implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private TestToolType toolType;

	private TestToolName toolName;

	private String toolId;

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

	public TestToolName getToolName() {
		return toolName;
	}

	public void setToolName(TestToolName toolName) {
		this.toolName = toolName;
	}

	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}
}

enum TestToolType {
	VM, Docker;
}

enum TestToolName {
	PC, MGWSim, TGen;
}