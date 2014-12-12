package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Test;

import org.hibernate.validator.constraints.NotBlank;

import com.ericsson.jcat.jcatwebapp.cusom.OpenstackFlavor;
import com.ericsson.jcat.jcatwebapp.cusom.SingleProcess;
import com.ericsson.jcat.jcatwebapp.cusom.TestingTool;
import com.ericsson.jcat.jcatwebapp.cusom.TrafficGenerator;
import com.ericsson.jcat.jcatwebapp.cusom.UserGroup;

public class CreateTestEnvForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = CreateTestEnvForm.NOT_BLANK_MESSAGE)
	private String name;

	private String description;

	private UserGroup group;

	private boolean remotePC;

	private OpenstackFlavor hwSet;

	private ArrayList<TrafficGenerator> envTrafficGenerator;

	private ArrayList<TestingTool> envTestingTool;

	private ArrayList<SingleProcess> envSingleProcess;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}

	public boolean getRemotePC() {
		return remotePC;
	}

	public void setRemotePC(Boolean remotePC) {
		this.remotePC = remotePC;
	}

	public OpenstackFlavor getHwSet() {
		return hwSet;
	}

	public void setHwSet(OpenstackFlavor hwSet) {
		this.hwSet = hwSet;
	}

	public ArrayList<TrafficGenerator> getEnvTrafficGenerator() {
		return envTrafficGenerator;
	}

	public void setEnvTrafficGenerator(ArrayList<TrafficGenerator> envTrafficGenerator) {
		this.envTrafficGenerator = envTrafficGenerator;
	}

	public ArrayList<TestingTool> getEnvTestingTool() {
		return envTestingTool;
	}

	public void setEnvTestingTool(ArrayList<TestingTool> envTestingTool) {
		this.envTestingTool = envTestingTool;
	}

	public ArrayList<SingleProcess> getEnvSingleProcess() {
		return envSingleProcess;
	}

	public void setEnvSingleProcess(ArrayList<SingleProcess> envSingleProcess) {
		this.envSingleProcess = envSingleProcess;
	}

	public TestEnv createTestEnv() {
		return new TestEnv(this.getName(), this.getDescription(), this.getGroup(), this.getRemotePC(), this.getHwSet(),
				this.getEnvTrafficGenerator(), this.getEnvTestingTool(), this.getEnvSingleProcess());
	}



}
