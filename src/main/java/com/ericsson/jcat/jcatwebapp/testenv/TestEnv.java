package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

	private String tgenDockerId;

	private ArrayList<TestingTool> envTT;

	private ArrayList<String> envSP;

	// private Map<String, String> dockerInstances;
	@OneToOne
	private StpInfo stp;

	@Deprecated
	private String stpIp;

	@Deprecated
	private String expertUser;

	@Deprecated
	private String expertPass;
	@Deprecated
	private String customerUser;
	@Deprecated
	private String customerPass;
	private boolean stpSet;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<TestTool> toolList = new ArrayList<TestTool>();

	public TestEnv() {
	}

	public TestEnv(String title, String text, String owner, String userGroup, boolean pcSet, String imageSet,
			String vmServerId, String mgwSimVmServerId, ArrayList<TrafficGenerator> envTG, String tgenDockerId,
			ArrayList<TestingTool> envTT, String stpName, String stpIp, String expertUser, String expertPass,
			String customerUser, String customerPass) {
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
		this.setTgenDockerId(tgenDockerId);
		this.setEnvTT(envTT);
		this.setStpIp(stpIp);
		this.setExpertUser(expertUser);
		this.setExpertPass(expertPass);
		this.setCustomerUser(customerUser);
		this.setCustomerPass(customerPass);
	}

	public TestEnv(String title, String text, String owner, String userGroup, boolean pcSet, String imageSet,
			String vmServerId, String mgwSimVmServerId, ArrayList<TrafficGenerator> envTG, String tgenDockerId,
			ArrayList<TestingTool> envTT, String stpName, String stpIp, String expertUser, String expertPass,
			String customerUser, String customerPass, ArrayList<TestTool> testToolList) {
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
		this.setTgenDockerId(tgenDockerId);
		this.setEnvTT(envTT);
		this.setStpIp(stpIp);
		this.setExpertUser(expertUser);
		this.setExpertPass(expertPass);
		this.setCustomerUser(customerUser);
		this.setCustomerPass(customerPass);
		this.setToolList(testToolList);
	}

	public long getCreated() {
		return created;
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

	public int getId() {
		return id;
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

	public StpInfo getStp() {
		return stp;
	}

	public String getStpIp() {
		return stpIp;
	}

	public String getTgenDockerId() {
		return tgenDockerId;
	}



	public List<TestTool> getToolList() {
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

	public boolean isShared() {
		return shared;
	}

	public boolean isStpSet() {
//		return ((stpIp != null && !stpIp.isEmpty()) || (stpName != null || !stpName.isEmpty())) ? true : false;
		return stpSet;
	}

	public void setCreated(long created) {
		this.created = created;
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

	public void setId(int id) {
		this.id = id;
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

	public void setPcSet(boolean pcSet) {
		this.pcSet = pcSet;
	}

	public void setShared(boolean isShared) {
		this.shared = isShared;
	}

	public void setStp(StpInfo stp) {
		this.stp = stp;
	}

	public void setStpIp(String stpIp) {
		this.stpIp = stpIp;
	}



	public void setStpSet(boolean stpSet) {
		this.stpSet = stpSet;
	}

	public void setTgenDockerId(String tgenDockerId) {
		this.tgenDockerId = tgenDockerId;
	}

	public void setToolList(ArrayList<TestTool> toolList) {
		this.toolList = toolList;
	}

	public void setToolList(List<TestTool> toolList) {
		this.toolList = toolList;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public void setVmServerId(String vmServerId) {
		this.vmServerId = vmServerId;
	}

	// public Map<String, String> getDockerInstances() {
	// return dockerInstances;
	// }
	//
	// public void setDockerInstances(Map<String, String> dockerInstances) {
	// this.dockerInstances = dockerInstances;
	// }

}
