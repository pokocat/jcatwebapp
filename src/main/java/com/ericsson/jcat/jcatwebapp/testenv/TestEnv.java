package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.validator.constraints.NotEmpty;

import com.ericsson.jcat.jcatwebapp.cusom.TestingTool;
import com.ericsson.jcat.jcatwebapp.cusom.TrafficGenerator;

@Entity
public class TestEnv {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotEmpty
	private String name;

	private String description;

	@Version
	private long created = Calendar.getInstance().getTimeInMillis();

	private String owner;
	
	private boolean shared;

	private String userGroup;

	private boolean pcSet;

	private String imageSet;

	private String vmServerId;
	
	private String mgwSimVmServerId;

	private ArrayList<TrafficGenerator> envTG;

	private ArrayList<TestingTool> envTT;

	private ArrayList<String> envSP;

//	private Map<String, String> dockerInstances;

	private String stpIp;

	private String expertUser;

	private String expertPass;

	private String customerUser;

	private String customerPass;

	public TestEnv() {
	}

	public TestEnv(String title, String text, String owner, String userGroup, boolean pcSet, String imageSet,
			String vmServerId,String mgwSimVmServerId, ArrayList<TrafficGenerator> envTG, ArrayList<TestingTool> envTT, String stpIp,
			String expertUser, String expertPass, String customerUser, String customerPass) {
		this.setName(title);
		this.setDescription(text);
		this.setOwner(owner);
		this.setShared(true);
		this.setUserGroup(userGroup);
		this.setPcSet(pcSet);
		this.setImageSet(imageSet);
		this.setVmServerId(vmServerId);
		this.setMgwSimVmServerId(mgwSimVmServerId);
		this.setEnvTG(envTG);
		this.setEnvTT(envTT);
		this.setStpIp(stpIp);
		this.setExpertUser(expertUser);
		this.setExpertPass(expertPass);
		this.setCustomerUser(customerUser);
		this.setCustomerPass(customerPass);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean isShared) {
		this.shared = isShared;
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

	public String getImageSet() {
		return imageSet;
	}

	public void setImageSet(String imageSet) {
		this.imageSet = imageSet;
	}

	public String getMgwSimVmServerId() {
		return mgwSimVmServerId;
	}

	public void setMgwSimVmServerId(String mgwSimVmServerId) {
		this.mgwSimVmServerId = mgwSimVmServerId;
	}

//	public Map<String, String> getDockerInstances() {
//		return dockerInstances;
//	}
//
//	public void setDockerInstances(Map<String, String> dockerInstances) {
//		this.dockerInstances = dockerInstances;
//	}

}
