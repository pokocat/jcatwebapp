package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotBlank;

import com.ericsson.jcat.jcatwebapp.enums.TestingTool;

public class CreateTestEnvForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = CreateTestEnvForm.NOT_BLANK_MESSAGE)
	private String name;

	private String description;

	private String owner;

	private String userGroup;

	private boolean pcSet;

	private String hwSet;

	private String imageSet;

	private ArrayList<TestingTool> envTT;

	private String stpName;

	private String stpIp;

	private String expertUser;

	private String expertPass;

	private String customerUser;

	private String customerPass;

	private ArrayList<TestTool> toolList = new ArrayList<TestTool>();

	private StpInfo stpInfo;

	public TestEnv createTestEnv() {
		return new TestEnv(this.getName(), this.getDescription(), this.getOwner(), false, this.getUserGroup(),
				this.getStpInfo(), this.getToolList());
	}

	@Override
	public String toString() {
		return "CreateTestEnvForm [" + (name != null ? "name=" + name + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (owner != null ? "owner=" + owner + ", " : "")
				+ (userGroup != null ? "userGroup=" + userGroup + ", " : "") + "pcSet=" + pcSet + ", "
				+ (hwSet != null ? "hwSet=" + hwSet + ", " : "")
				+ (imageSet != null ? "imageSet=" + imageSet + ", " : "")
				+ (envTT != null ? "envTT=" + envTT + ", " : "") + (stpName != null ? "stpName=" + stpName + ", " : "")
				+ (stpIp != null ? "stpIp=" + stpIp + ", " : "")
				+ (expertUser != null ? "expertUser=" + expertUser + ", " : "")
				+ (expertPass != null ? "expertPass=" + expertPass + ", " : "")
				+ (customerUser != null ? "customerUser=" + customerUser + ", " : "")
				+ (customerPass != null ? "customerPass=" + customerPass + ", " : "")
				+ (toolList != null ? "toolList=" + toolList + ", " : "")
				+ (stpInfo != null ? "stpInfo=" + stpInfo : "") + "]";
	}

	public String getCustomerPass() {
		return customerPass;
	}

	public String getCustomerUser() {
		return customerUser;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<TestingTool> getEnvTT() {
		return envTT;
	}

	public String getExpertPass() {
		return expertPass;
	}

	public String getExpertUser() {
		return expertUser;
	}

	public String getHwSet() {
		return hwSet;
	}

	public String getImageSet() {
		return imageSet;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public String getStpIp() {
		return stpIp;
	}

	public String getStpName() {
		return stpName;
	}

	public ArrayList<TestTool> getToolList() {
		return toolList;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public boolean isPcSet() {
		return pcSet;
	}

	public void setCustomerPass(String customerPass) {
		this.customerPass = customerPass;
	}

	public void setCustomerUser(String customerUser) {
		this.customerUser = customerUser;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEnvTT(ArrayList<TestingTool> envTT) {
		this.envTT = envTT;
	}

	public void setExpertPass(String expertPass) {
		this.expertPass = expertPass;
	}

	public void setExpertUser(String expertUser) {
		this.expertUser = expertUser;
	}

	public void setHwSet(String hwSet) {
		this.hwSet = hwSet;
	}

	public void setImageSet(String imageSet) {
		this.imageSet = imageSet;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setPcSet(boolean pcSet) {
		this.pcSet = pcSet;
	}

	public void setStpIp(String stpIp) {
		this.stpIp = stpIp;
	}

	public void setStpName(String stpName) {
		this.stpName = stpName;
	}

	public void setToolList(ArrayList<TestTool> toolList) {
		this.toolList = toolList;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public StpInfo getStpInfo() {
		return stpInfo;
	}

	public void setStpInfo(StpInfo stpInfo) {
		this.stpInfo = stpInfo;
	}

}
