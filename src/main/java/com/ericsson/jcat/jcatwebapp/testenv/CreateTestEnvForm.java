package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import junit.framework.Test;

import org.hibernate.validator.constraints.NotBlank;

import com.ericsson.jcat.jcatwebapp.cusom.OpenstackFlavor;
import com.ericsson.jcat.jcatwebapp.cusom.OpenstackImage;
import com.ericsson.jcat.jcatwebapp.cusom.SingleProcess;
import com.ericsson.jcat.jcatwebapp.cusom.TestingTool;
import com.ericsson.jcat.jcatwebapp.cusom.TrafficGenerator;
import com.ericsson.jcat.jcatwebapp.cusom.UserGroup;

@XmlRootElement
public class CreateTestEnvForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = CreateTestEnvForm.NOT_BLANK_MESSAGE)
	private String name;

	private String description;
	
	private String owner;

	private UserGroup group;

	private boolean pcSet;

	private OpenstackFlavor hwSet;

	private OpenstackImage imageSet;

	private ArrayList<TrafficGenerator> envTrafficGenerator;

	private ArrayList<TestingTool> envTestingTool;

	private ArrayList<SingleProcess> envSingleProcess;

	private String stpIp;

	private String expertUser;

	private String expertPass;

	private String customerUser;

	private String customerPass;

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

	public boolean getPcSet() {
		return pcSet;
	}

	public void setPcSet(Boolean pcSet) {
		this.pcSet = pcSet;
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

	public OpenstackImage getImageSet() {
		return imageSet;
	}

	public void setImageSet(OpenstackImage imageSet) {
		this.imageSet = imageSet;
	}

	public String getStpIp() {
		return stpIp;
	}

	public void setStpIp(String stpIp) {
		this.stpIp = stpIp;
	}

	public String getExpertUser() {
		return expertUser;
	}

	public void setExpertUser(String expertUser) {
		this.expertUser = expertUser;
	}

	public String getExpertPass() {
		return expertPass;
	}

	public void setExpertPass(String expertPass) {
		this.expertPass = expertPass;
	}

	public String getCustomerUser() {
		return customerUser;
	}

	public void setCustomerUser(String customerUser) {
		this.customerUser = customerUser;
	}

	public String getCustomerPass() {
		return customerPass;
	}

	public void setCustomerPass(String customerPass) {
		this.customerPass = customerPass;
	}

	public void setPcSet(boolean pcSet) {
		this.pcSet = pcSet;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public TestEnv createTestEnv() {
		return new TestEnv(this.getName(), this.getDescription(), this.getOwner(), this.getGroup(), this.getPcSet(), this.getHwSet(),
				this.getImageSet(), this.getEnvTrafficGenerator(), this.getEnvTestingTool(), this.getStpIp(),
				this.getExpertUser(), this.getExpertPass(), this.getCustomerUser(), this.getCustomerPass());
	}

	@Override
	public String toString() {
		return "CreateTestEnvForm [" + (name != null ? "name=" + name + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (owner != null ? "owner=" + owner + ", " : "") + (group != null ? "group=" + group + ", " : "")
				+ "pcSet=" + pcSet + ", " + (hwSet != null ? "hwSet=" + hwSet + ", " : "")
				+ (imageSet != null ? "imageSet=" + imageSet + ", " : "")
				+ (envTrafficGenerator != null ? "envTrafficGenerator=" + envTrafficGenerator + ", " : "")
				+ (envTestingTool != null ? "envTestingTool=" + envTestingTool + ", " : "")
				+ (envSingleProcess != null ? "envSingleProcess=" + envSingleProcess + ", " : "")
				+ (stpIp != null ? "stpIp=" + stpIp + ", " : "")
				+ (expertUser != null ? "expertUser=" + expertUser + ", " : "")
				+ (expertPass != null ? "expertPass=" + expertPass + ", " : "")
				+ (customerUser != null ? "customerUser=" + customerUser + ", " : "")
				+ (customerPass != null ? "customerPass=" + customerPass : "") + "]";
	}
	
	
}
