package com.ericsson.jcat.jcatwebapp.testenv;

import org.hibernate.validator.constraints.NotEmpty;

import com.ericsson.jcat.jcatwebapp.cusom.OpenstackFlavor;
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
	private Calendar created = Calendar.getInstance();

	private UserGroup userGroup;

	private boolean useRPC;

	private OpenstackFlavor hwSet;

	private ArrayList<TrafficGenerator> envTG;
	private ArrayList<TestingTool> envTT;
	private ArrayList<SingleProcess> envSP;

	public TestEnv() {
	}

	public TestEnv(String title, String text, UserGroup userGroup, boolean useRPC, OpenstackFlavor hwSet,
			ArrayList<TrafficGenerator> envTG, ArrayList<TestingTool> envTT, ArrayList<SingleProcess> envSP) {
		this.name = title;
		this.description = text;
		this.setGroup(userGroup);
		this.setUseRPC(useRPC);
		this.setHwSet(hwSet);
		this.setEnvTG(envTG);
		this.setEnvTT(envTT);
		this.setEnvSP(envSP);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
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
		return useRPC;
	}

	public void setUseRPC(boolean useRPC2) {
		this.useRPC = useRPC2;
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

}
