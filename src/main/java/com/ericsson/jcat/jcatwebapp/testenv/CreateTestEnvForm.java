package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.ArrayList;
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

@Entity
public class CreateTestEnvForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@Id
	@GeneratedValue
	private Integer id;

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public boolean isPcSet() {
		return pcSet;
	}

	public void setPcSet(boolean pcSet) {
		this.pcSet = pcSet;
	}

	public String getVmServerId() {
		return vmServerId;
	}

	public void setVmServerId(String vmServerId) {
		this.vmServerId = vmServerId;
	}

	public String getHwSet() {
		return hwSet;
	}

	public void setHwSet(String hwSet) {
		this.hwSet = hwSet;
	}

	public String getImageSet() {
		return imageSet;
	}

	public void setImageSet(String imageSet) {
		this.imageSet = imageSet;
	}

	public ArrayList<TrafficGenerator> getEnvTG() {
		return envTG;
	}

	public void setEnvTG(ArrayList<TrafficGenerator> envTG) {
		this.envTG = envTG;
	}


	public ArrayList<TestingTool> getEnvTT() {
		return envTT;
	}

	public void setEnvTT(ArrayList<TestingTool> envTT) {
		this.envTT = envTT;
	}

	public ArrayList<String> getEnvSP() {
		return envSP;
	}

	public void setEnvSP(ArrayList<String> envSP) {
		this.envSP = envSP;
	}

	public String getStpName() {
		return stpName;
	}

	public void setStpName(String stpName) {
		this.stpName = stpName;
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

	// public Map<String, String> getDockerInstances() {
	// return dockerInstances;
	// }
	//
	// public void setDockerInstances(Map<String, String> dockerInstances) {
	// this.dockerInstances = dockerInstances;
	// }

	public String getMgwSimVmServerId() {
		return mgwSimVmServerId;
	}

	public void setMgwSimVmServerId(String mgwSimVmServerId) {
		this.mgwSimVmServerId = mgwSimVmServerId;
	}

	public String getTgenDockerId() {
		return tgenDockerId;
	}

	public void setTgenDockerId(String tgenDockerId) {
		this.tgenDockerId = tgenDockerId;
	}

	public TestEnv createTestEnv() throws FlavorNotFoundException, ImageNotFoundException, VmCreationFailureException,
			TimeoutException {
		return new TestEnv(this.getName(), this.getDescription(), this.getOwner(), this.getUserGroup(), this.isPcSet(),
				this.getImageSet(), this.getVmServerId(), this.getMgwSimVmServerId(), this.getEnvTG(),
				this.getTgenDockerId(), this.getEnvTT(), this.getStpName(), this.getStpIp(), this.getExpertUser(),
				this.getExpertPass(), this.getCustomerUser(), this.getCustomerPass());
	}

	@Override
	public String toString() {
		return "CreateTestEnvForm [" + (id != null ? "id=" + id + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
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
				+ (customerPass != null ? "customerPass=" + customerPass : "") + "]";
	}

}
