package com.ericsson.jcat.jcatwebapp.testenv;

import org.hibernate.validator.constraints.NotEmpty;

import com.ericsson.jcat.jcatwebapp.cusom.OpenstackFlavor;
import com.ericsson.jcat.jcatwebapp.cusom.OpenstackImage;
import com.ericsson.jcat.jcatwebapp.cusom.SingleProcess;
import com.ericsson.jcat.jcatwebapp.cusom.TestingTool;
import com.ericsson.jcat.jcatwebapp.cusom.TrafficGenerator;
import com.ericsson.jcat.jcatwebapp.cusom.UserGroup;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Calendar;

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

	private OpenstackFlavor hwSet;

	private OpenstackImage imageSet;

	private ArrayList<TrafficGenerator> envTG;

	private ArrayList<TestingTool> envTT;

	private ArrayList<SingleProcess> envSP;

	private String stpIp;

	private String expertUser;

	private String expertPass;

	private String customerUser;

	private String customerPass;

	public TestEnv() {
	}

	public TestEnv(String title, String text,String owner, UserGroup userGroup, boolean pcSet, OpenstackFlavor hwSet,
			OpenstackImage imageSet, ArrayList<TrafficGenerator> envTG, ArrayList<TestingTool> envTT, String stpIp,
			String expertUser, String expertPass, String customerUser, String customerPass) {
		this.name = title;
		this.description = text;
		this.setOwner(owner);
		this.setGroup(userGroup);
		this.setUseRPC(pcSet);
		this.setHwSet(hwSet);
		this.setImageSet(imageSet);
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

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public String getName() {
		return name;
	}

	public void setName(String title) {
		this.name = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String text) {
		this.description = text;
	}

	public UserGroup getGroup() {
		return userGroup;
	}

	public void setGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public boolean getUseRPC() {
		return pcSet;
	}

	public void setUseRPC(boolean useRPC2) {
		this.pcSet = useRPC2;
	}

	public OpenstackFlavor getHwSet() {
		return hwSet;
	}

	public void setHwSet(OpenstackFlavor hwSet) {
		this.hwSet = hwSet;
	}

	public ArrayList<TrafficGenerator> getEnvTG() {
		return envTG;
	}

	public void setEnvTG(ArrayList<TrafficGenerator> envTG2) {
		this.envTG = envTG2;
	}

	public ArrayList<TestingTool> getEnvTT() {
		return envTT;
	}

	public void setEnvTT(ArrayList<TestingTool> envTT) {
		this.envTT = envTT;
	}

	public ArrayList<SingleProcess> getEnvSP() {
		return envSP;
	}

	public void setEnvSP(ArrayList<SingleProcess> envSP) {
		this.envSP = envSP;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
