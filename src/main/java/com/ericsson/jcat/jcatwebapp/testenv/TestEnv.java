package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.validator.constraints.NotEmpty;
import org.openstack4j.openstack.compute.domain.NovaServer.Servers;

import com.ericsson.jcat.jcatwebapp.cusom.TestingTool;
import com.ericsson.jcat.jcatwebapp.cusom.TrafficGenerator;
import com.ericsson.jcat.jcatwebapp.cusom.UserGroup;

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

	private UserGroup userGroup;

	private boolean pcSet;
	
	private String imageSet;

	private String vmServerId;

	private ArrayList<TrafficGenerator> envTG;

	private ArrayList<TestingTool> envTT;

	private ArrayList<String> envSP;

	private String stpIp;

	private String expertUser;

	private String expertPass;

	private String customerUser;

	private String customerPass;

	public TestEnv() {
	}

	public TestEnv(String title, String text, String owner, UserGroup userGroup, boolean pcSet, String imageSet, String vmServerId,
			ArrayList<TrafficGenerator> envTG, ArrayList<TestingTool> envTT, String stpIp, String expertUser,
			String expertPass, String customerUser, String customerPass) {
		this.setName(title);
		this.setDescription(text);
		this.setOwner(owner);
		this.setUserGroup(userGroup);
		this.setPcSet(pcSet);
		this.setImageSet(imageSet);
		this.setVmServerId(vmServerId);
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

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
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

}
