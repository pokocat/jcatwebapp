package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import com.ericsson.jcat.jcatwebapp.cusom.TestingTool;
import com.ericsson.jcat.jcatwebapp.cusom.TrafficGenerator;
import com.ericsson.jcat.osadapter.exceptions.FlavorNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.ImageNotFoundException;
import com.ericsson.jcat.osadapter.exceptions.VmCreationFailureException;


public class CreateTestEnvForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = CreateTestEnvForm.NOT_BLANK_MESSAGE)
	private String name;

	private String description;

	private String owner;

	private String userGroup;

	private boolean pcSet;

	private String vmServerId;

	private String hwSet;

	private String imageSet;

	private ArrayList<TrafficGenerator> envTG;

	private String mgwSimVmServerId;

	private ArrayList<TestingTool> envTT;

	private ArrayList<String> envSP;

	// private Map<String, String> dockerInstances;

	private String tgenDockerId;

	private String stpName;

	private String stpIp;

	private String expertUser;

	private String expertPass;

	private String customerUser;

	private String customerPass;

	private ArrayList<TestTool> toolList = new ArrayList<TestTool>();

	public TestEnv createTestEnv(StpInfo stp) throws FlavorNotFoundException, ImageNotFoundException, VmCreationFailureException,
			TimeoutException {
		 TestEnv testEnv = new TestEnv(this.getName(), this.getDescription(), this.getOwner(), this.getUserGroup(), this.isPcSet(),
				this.getImageSet(), this.getVmServerId(), this.getMgwSimVmServerId(), this.getEnvTG(),
				this.getTgenDockerId(), this.getEnvTT(), this.getStpName(), this.getStpIp(), this.getExpertUser(),
				this.getExpertPass(), this.getCustomerUser(), this.getCustomerPass(), this.getToolList());
		 testEnv.setStp(stp);
		 return testEnv;
	}

	@Override
	public String toString() {
		return "CreateTestEnvForm [" + (name != null ? "name=" + name + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (owner != null ? "owner=" + owner + ", " : "")
				+ (userGroup != null ? "userGroup=" + userGroup + ", " : "") + "pcSet=" + pcSet + ", "
				+ (vmServerId != null ? "vmServerId=" + vmServerId + ", " : "")
				+ (hwSet != null ? "hwSet=" + hwSet + ", " : "")
				+ (imageSet != null ? "imageSet=" + imageSet + ", " : "")
				+ (envTG != null ? "envTG=" + envTG + ", " : "")
				+ (mgwSimVmServerId != null ? "mgwSimVmServerId=" + mgwSimVmServerId + ", " : "")
				+ (envTT != null ? "envTT=" + envTT + ", " : "") + (envSP != null ? "envSP=" + envSP + ", " : "")
				+ (tgenDockerId != null ? "tgenDockerId=" + tgenDockerId + ", " : "")
				+ (stpName != null ? "stpName=" + stpName + ", " : "") + (stpIp != null ? "stpIp=" + stpIp + ", " : "")
				+ (expertUser != null ? "expertUser=" + expertUser + ", " : "")
				+ (expertPass != null ? "expertPass=" + expertPass + ", " : "")
				+ (customerUser != null ? "customerUser=" + customerUser + ", " : "")
				+ (customerPass != null ? "customerPass=" + customerPass + ", " : "")
				+ (toolList != null ? "toolList=" + toolList : "") + "]";
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

	public ArrayList<String> getEnvSP() {
		return envSP;
	}

	public ArrayList<TrafficGenerator> getEnvTG() {
		return envTG;
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

	public String getMgwSimVmServerId() {
		return mgwSimVmServerId;
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

	public String getTgenDockerId() {
		return tgenDockerId;
	}

	public ArrayList<TestTool> getToolList() {
		return toolList;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public String getVmServerId() {
		return vmServerId;
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

	public void setEnvSP(ArrayList<String> envSP) {
		this.envSP = envSP;
	}

	public void setEnvTG(ArrayList<TrafficGenerator> envTG) {
		this.envTG = envTG;
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

	public void setMgwSimVmServerId(String mgwSimVmServerId) {
		this.mgwSimVmServerId = mgwSimVmServerId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	// public Map<String, String> getDockerInstances() {
	// return dockerInstances;
	// }
	//
	// public void setDockerInstances(Map<String, String> dockerInstances) {
	// this.dockerInstances = dockerInstances;
	// }

	public void setPcSet(boolean pcSet) {
		this.pcSet = pcSet;
	}

	public void setStpIp(String stpIp) {
		this.stpIp = stpIp;
	}

	public void setStpName(String stpName) {
		this.stpName = stpName;
	}

	public void setTgenDockerId(String tgenDockerId) {
		this.tgenDockerId = tgenDockerId;
	}

	public void setToolList(ArrayList<TestTool> toolList) {
		this.toolList = toolList;
	}

	

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public void setVmServerId(String vmServerId) {
		this.vmServerId = vmServerId;
	}

}
